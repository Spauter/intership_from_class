package com.yc.demo.lexicon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class BestMatchingTask implements Callable<WordTips> {
    private List<String>words;
    private String word;
    private int startIndex;
    private int endWithIndex;

    public BestMatchingTask(List<String>list,String word,int startIndex,int endWithIndex){
        this.endWithIndex=endWithIndex;
        this.startIndex=startIndex;
        this.words=list;
        this.word=word;
    }

    @Override
    public WordTips call() {
        List<String>minDistanceWords=new ArrayList<>();//最短距离单词列表
        int minDistance=Integer.MAX_VALUE;
        int distance;//计算的距离
        for(int i=startIndex;i<endWithIndex;i++){
           distance= LevenshteinDistance.distance(word,words.get(i));
            if(distance<minDistance){
                minDistanceWords.clear();
                minDistance=distance;
                minDistanceWords.add(words.get(i));
            } else if (distance==minDistance) {
                minDistanceWords.add(words.get(i));
            }
        }
        WordTips wordTips=new WordTips();
        wordTips.setDistance(minDistance);
        wordTips.setWords(minDistanceWords);
        return wordTips;
    }
}
