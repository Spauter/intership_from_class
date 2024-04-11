package com.bloducspauter.futuretest;

import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Intel core i5-10210U 1.60GHz
 *
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

class CompletableFutureDemo3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "------Come In");
            //sleep  5 seconds
            TimeUnit.MICROSECONDS.sleep(5000000);
            return "Task over";
        });
        Thread thread = new Thread(futureTask, "t1");
        thread.start();
        long startTime = System.currentTimeMillis();
        /*
            Once using the method "get", it may be blocked because it's calculate is not finished
            If you use method "isDome", It may cause CPU idling
            Uncomment this below code, The following loop statement needs to wait the thread done
         */
//        System.out.println(futureTask.get());
        System.out.println(Thread.currentThread().getName() + "------Busy for other task");
        long endTime = System.currentTimeMillis();
        System.out.println("----cost time: " + (endTime - startTime) + "ms");
        while (true) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get() + "------");
                break;
            } else {
                //To pause 1s
                TimeUnit.MICROSECONDS.sleep(1000000);
                System.out.println("The task t1 is running, don't urge!");
            }
        }
    }
}