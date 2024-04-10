package com.yc.demo.lexicon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class BestMatchingConcurrentCalculation {
    public static WordTips getBestMatchingWords(String word, List<String> dictionary) throws InterruptedException, ExecutionException {
//       1.创建任务数
        int munCores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(munCores);
        List<BestMatchingTask> tasks = getBestMatchingTasks(word, dictionary, munCores);
//        将上面创建的FutureTask提交给Executor线程执行,如果任务没有完成,invokeALl()阻塞处处
        List<Future<WordTips>> futureList = executor.invokeAll(tasks);
//        任务完成了,停止所有任务;
        executor.shutdown();
//      通过FutureTask.get获取以上任务执行结果
        int minDistance = Integer.MAX_VALUE;
        List<String> words = new ArrayList<>();
        for (Future<WordTips> futures : futureList) {
            WordTips wordTips = futures.get();
            if (wordTips.getDistance() < minDistance) {
                words.clear();
                minDistance = wordTips.getDistance();
                words.addAll(wordTips.getWords());
            } else if (wordTips.getDistance() == minDistance) {
//                这个任务找到最短距离,说明与已知的一样,则合并单词表
                words.addAll(wordTips.getWords());
            }
        }
        WordTips wordTips = new WordTips();
        wordTips.setWords(words);
        wordTips.setDistance(minDistance);
        return wordTips;
    }

    private static List<BestMatchingTask> getBestMatchingTasks(String word, List<String> dictionary, int munCores) {
//      计算每个任务对应的词汇量
        int size = dictionary.size();
        int step = size / munCores;
        int startIndex, endWithIndex;
//        创建BestMatchingTask
        List<BestMatchingTask> tasks = new ArrayList<>();
        for (int i = 0; i < munCores; i++) {
            startIndex = i * step;
            if (i == munCores - 1) {
                endWithIndex = size;
            } else {
                endWithIndex = (i + 1) * step;
            }
            BestMatchingTask task = new BestMatchingTask(dictionary, word, startIndex, endWithIndex);
            tasks.add(task);
        }
        return tasks;
    }
}
