package com.yc.demo.lexicon;



import java.util.List;

/*

开发一个单词匹配系统,给定一个单词.输出与这个单词编辑距离最短的n个词,考虑性能
技术点：
    1.实现编辑距离算法: a)递归 b)动态规划
    2.以多核开发的方式实现距离计算
      1个词库和词库中所有单词的计算距离,找最小的
开发步骤：
    1.读取词库,将词库词存在一个集合
    2.实现编辑距离算法
    3.任务调度:
      XXXTask任务类：
      XXX线程池的控制类
    4.结果类 => 编辑距离最小的单词列表
        distance
        List<String>
    5.主程序调度
 */
public class WordTips {
    private int distance;//距离
    private List<String>words;//单词表

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
