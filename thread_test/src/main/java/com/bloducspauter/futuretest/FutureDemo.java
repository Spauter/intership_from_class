package com.bloducspauter.futuretest;
import java.util.concurrent.*;

/**
 * CPU:Intel core i5-10210U 1.60GHz
 * @author Bloduc Spauter
 */
public class FutureDemo {
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

final class FutureDemo2 {
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
/**
 * Once using the method {@link Future#get()}, it may be blocked.
 * Because its calculating is not finished.
 * If you use the method {@link Future#isDone()}, It may cause CPU wasting of resources
 * <p>
 * Conclusion: The result of getting is unfriendly in class {@link Future}.
 * Only by blocking or polling can we get it.
 * </p>
 * @author Bloduc Spauter
 */
final class FutureDemo3 {
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
        /*  Uncomment this below code, The following loop statement needs to wait the thread done*/
        //System.out.println(futureTask.get());
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

/**
 * Using class {@link Future} is OK for simple business.
 * It is a call-back notification that {@link Future} informs we when finished a task.
 * <p>
 * Using method {@link Future#isDone()} may waste CPU resources.
 * <p>
 * To create an asynchronous tasks, we need to combine {@link Future} with the thread poll.
 * @author Bloduc Spauter
 */
final class FutureDemo4{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
        If you don't have a specified Executor way,
        you can use the default thread pool "ForkJoinPool.commonPool",
        and as its thread pool to run this asynchronous code.
         */
        ExecutorService executorService=Executors.newFixedThreadPool(3);
        //Running an asynchronous method, and no a return statement
        CompletableFuture<Void> completableFuture=CompletableFuture.runAsync(()->{
            //ForkJoinPool-worker-3
            System.out.println(Thread.currentThread().getName());
            try {
                //sleep 1s
                TimeUnit.MICROSECONDS.sleep(1000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
                //The result is "pool-1-thread-1"
                //Comment this below code;
                // the first sentence printed will turn "ForkJoinPool-worker-1"
                ,executorService
        );
        //null.
        executorService.shutdown();
        System.out.println(completableFuture.get());
    }
}

final class FutureDemo5{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //It will return a String statement
        CompletableFuture<String> completableFuture=CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName());
            try {
                //sleep 1s
                TimeUnit.MICROSECONDS.sleep(1000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Hello!This is  supplyAsync";
        });
        //Hello!This is supplyAsync
        System.out.println(completableFuture.get());

    }
}