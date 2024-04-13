package com.bloducspauter.future;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class is uses to handle a calculated result
 *
 * @author Bloduc Spauter
 */
public class CompletableFutureDemoTest {
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted,Because of InterruptedException");
            }
            return "Test1";
        });
        //The method CompletableFuture.join() is same as it
        System.out.println(completableFuture.get());
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                //sleep 3s
                TimeUnit.MICROSECONDS.sleep(3000000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted,Because of InterruptedException");
            }
            return "Test2";
        });
        //If I just want to wait to 2 seconds, it will throw TimeoutException
        try {
            System.out.println(completableFuture.get(2L, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Time overt, I can not wait!");
        }
        //it returns the result "Test2" without exception after 4 seconds
        System.out.println(completableFuture.get(2L, TimeUnit.SECONDS));
    }

    @Test
    public void test3() throws InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                //sleep 2s
                TimeUnit.MICROSECONDS.sleep(2000000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted,Because of InterruptedException");
            }
            return "Test3";
        });
        TimeUnit.MICROSECONDS.sleep(1000000);
        //Because this thread is not finished, it will return a provisional response
        System.out.println(completableFuture.getNow("Provisional response"));
        TimeUnit.MICROSECONDS.sleep(2000000);
        //Thread done, it returns a statement "Test3"
        System.out.println(completableFuture.getNow("Provisional response"));
    }

    @Test
    public void test4() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                //sleep 2s
                TimeUnit.MICROSECONDS.sleep(2000000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted,Because of InterruptedException");
            }
            return "Test4";
        });
        //If unfinished, interrupting this tread and return it directly
        System.out.println(completableFuture.complete("completeValue") + completableFuture.join());
    }

    /**
     * To simulate cooking a fish.
     * However, you will don't see the other printed statement except "main---The main thread is busy"
     */
    @Test
    public void test5() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.MICROSECONDS.sleep(1000000);
                    } catch (InterruptedException e) {
                        System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
                    }
                    System.out.println("Obtained a fish");
                    return "Obtained a fish";
                }, executorService)
                //Simulating step one
                .thenApply(s -> {
                    try {
                        TimeUnit.MICROSECONDS.sleep(1000000);
                    } catch (InterruptedException e) {
                        System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
                    }
                    System.out.println("Search for seasoning");
                    return s + "Searching for seasoning";
                })
                .thenApply(s -> {
                    try {
                        TimeUnit.MICROSECONDS.sleep(1000000);
                        /*
                        To simulate an exception, uncomment it, this thread will be interrupted.
                        Please run it in the main method.

                         */

//                        int i=1/0;
                    } catch (InterruptedException e) {
                        System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
                    }
                    System.out.println("Done.Ready to find a pot");
                    return s + "Done.Ready to find a pot";
                })
                .thenApply(s -> {
                    try {
                        TimeUnit.MICROSECONDS.sleep(1000000);
                    } catch (InterruptedException e) {
                        System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
                    }
                    System.out.println("Everything is OK. Cooking");
                    return s + "Everything is OK";
                }).whenComplete((v, e) -> {
                    if (e == null) {
                        System.out.println("Cooked a fish");
                    }
                }).exceptionally(e -> {
                    System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
                    System.out.println("Sorry that I fell through");
                    return "Sorry that I fell through";
                });
        System.out.println(Thread.currentThread().getName() + "---The main thread is busy");
        executorService.shutdown();
    }


    /**
     * To run the main method, you will see the complete print results.
     * <p>
     * Explanation of the behavior difference between running the code within a {@link Test} method {@link #test5()} and running it within the main method.
     * <p>
     * In a JUnit test case, the test methods are executed by JUnit's test runner in a controlled environment.
     * When you run the test method {@link #test5()}, JUnit starts a new thread to execute the test method,
     * but it doesn't wait for asynchronous tasks to complete before considering the test method complete.
     * This means that JUnit may terminate the test before the asynchronous tasks are finished, leading to incomplete output.
     * On the other hand, when you run the code in the main method,
     * the main thread is not terminated until all other threads (including the ones spawned by CompletableFuture) have completed.
     * Therefore, you see the complete output when running the code in the main method.
     * <p>
     * To ensure that the asynchronous tasks are complete before the test method finishes, you can use mechanisms provided by JUnit,
     * such as {@link CompletableFuture<String>#join()} to wait for completion explicitly
     * or use JUnit's support for asynchronous testing using CompletableFuture or other means, depending on your testing requirements.
     * <p>
     */
    public static void main(String[] args) {
        new CompletableFutureDemoTest().test5();
    }

}

/**
 * {@link CompletableFuture#thenRun(Runnable)} : After Task A completed, go to Task B,
 * and B don't need A's return result.
 * <p>
 * {@link CompletableFuture#thenAccept(Consumer)} :After Task A completed,
 * go to Task B,
 * and B needs A's return result, but B don't have a return statement.
 * <p>
 * {@link CompletableFuture#thenApply(Function)} After Task A completed,
 * go to Task B。Not only B needs A's result, but have a return value
 */
class CompletableFutureDemoTest2 {
    public static void main(String[] args) {
        //null
        System.out.println(CompletableFuture.supplyAsync(() -> "one")
                .thenRun(() -> {
                })
                .join());
        //Two/n null
        System.out.println(CompletableFuture.supplyAsync(() -> "Two")
                .thenAccept(System.out::println)
                .join());
        //Three/t Four
        System.out.println(CompletableFuture.supplyAsync(() -> "Three")
                .thenApply(r -> r + "\t Four")
                .join());
    }
}

/**
 * Simulating the result:Who is the winner
 * {@link CompletableFuture#applyToEither(CompletionStage, Function)}: to select the faster choice
 */
class CompletableFutureDemoTest3 {
    public static void main(String[] args) {
        CompletableFuture<String> playerA = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(3);
            } catch (InterruptedException e) {
                System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
            }
            System.out.println("Player A come in");
            return "player A";
        });

        CompletableFuture<String> playerB = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
            }
            System.out.println("Player B come in");
            return "player B";
        });
        //If you set the same sleep time, the winner is playerA
        CompletableFuture<String> result = playerA.applyToEither(playerB, f -> f + " is winner");
        System.out.println(Thread.currentThread().getName() + "---" + result.join());
    }
}

/**
 * {@link CompletableFuture#thenCombine(CompletionStage, BiFunction)} will wait all threads done and combine then.
 */
class CompletableFutureDemoTest4 {
    public static void main(String[] args) {
        CompletableFuture<Integer> playerA = CompletableFuture.supplyAsync(() -> {
            int score = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.MICROSECONDS.sleep(score * 1000000);
            } catch (InterruptedException e) {
                System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
            }
            System.out.println("Player A's score is:" + score);
            return score;
        });
        CompletableFuture<Integer> playerB = CompletableFuture.supplyAsync(() -> {
            int score = ThreadLocalRandom.current().nextInt(10);
            try {
                //waiting for 1 to 10 seconds
                TimeUnit.MICROSECONDS.sleep(score * 1000000);
            } catch (InterruptedException e) {
                System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
            }
            System.out.println("Player B's score is:" + score);
            return score;
        });
        CompletableFuture<Integer> result = playerA.thenCombine(playerB, (x, y) -> {
            System.out.println("Player A and B are combined:" + (x + y));
            return x + y;
        });
        System.out.println("Player A and B are combined:" + result.join());
    }
}

class CompletableFutureDemoTest5 {
    public static void main(String[] args) {
        CompletableFuture<Integer> allThreads = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(2000000);
            } catch (InterruptedException e) {
                System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
            }
            System.out.println("The first thread is " + Thread.currentThread().getName() + ".Please wait for 2S");
            return 2;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(3000000);
            } catch (InterruptedException e) {
                System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
            }
            System.out.println("The second thread is " + Thread.currentThread().getName() + ".Please wait for 1S");
            return 1;
        }), Integer::sum).thenCombine(CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.MICROSECONDS.sleep(1000000);
            } catch (InterruptedException e) {
                System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
            }
            System.out.println("The third thread is " + Thread.currentThread().getName() + ".Please wait for 3S");
            return 3;
        }),Integer::sum).thenCombine(CompletableFuture.supplyAsync(()->{
                    try {
                        TimeUnit.MICROSECONDS.sleep(4000000);
                    } catch (InterruptedException e) {
                        System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
                    }
                    System.out.println("The fourth thread is "+Thread.currentThread().getName()+".Please wait for 4S");
                    return 4;
                }),Integer::sum)
                .whenComplete((v, e) -> {
                    if (e == null) {
                        System.out.println("All threads done, cost: "+v+"S");
                    }
                }).exceptionally(e->{
                    System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
                    return -1;
                });
        long startTime = System.currentTimeMillis();
        //about 4s
        System.out.println("All threads done, cost: "+allThreads.join()+"S");
        long endTime = System.currentTimeMillis();
        System.out.println("----cost time: " + (endTime - startTime) + "ms");
    }
}