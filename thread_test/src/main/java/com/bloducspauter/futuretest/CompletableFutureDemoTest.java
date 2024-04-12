package com.bloducspauter.futuretest;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
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
    public void test3() throws ExecutionException, InterruptedException, TimeoutException {
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
    public void test4() throws ExecutionException, InterruptedException, TimeoutException {
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
       System.out.println(completableFuture.complete("completeValue")+completableFuture.join());
   }

}
