package com.bloducspauter.futuretest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Intel core i5-10210U 1.60GHz
 * @author Bloduc Spauter
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        //Test one by one
        TimeUnit.MICROSECONDS.sleep(1000);
        TimeUnit.MICROSECONDS.sleep(5000);
        TimeUnit.MICROSECONDS.sleep(3000);
        long endTime = System.currentTimeMillis();
        //about 11ms
        System.out.println("----cost time: " + (endTime - startTime) + "ms");
        System.out.println(Thread.currentThread().getName() + "\t --end");
    }
}

class CompletableFutureDemo2 {
    static ExecutorService service = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        //Insert your code
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task Over";
        });
        service.submit(futureTask);

        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task Over";
        });
        service.submit(futureTask2);

        FutureTask<String> futureTask3 = new FutureTask<>(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task Over";
        });
        service.submit(futureTask3);
        service.shutdown();
        long endTime = System.currentTimeMillis();
        //about 3ms
        System.out.println("----cost time: " + (endTime - startTime) + "ms");
    }
}