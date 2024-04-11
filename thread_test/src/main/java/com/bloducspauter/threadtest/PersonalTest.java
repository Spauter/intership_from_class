package com.bloducspauter.threadtest;

import org.junit.Test;

/**
 * CPU:Intel core i5-10210U 1.60GHz
 * @author Bloduc Sputer
 */
public class PersonalTest {
    /**
     * This methods {@link ThreadTest1} and {@link ThreadTest2} are many different results from every execution
     * <p>
     *     Method one : typing this threads one by one
     * </p>
     */
    @Test
    public void test1() throws InterruptedException {
      long startTime=System.currentTimeMillis();
        System.out.println(Thread.currentThread().isDaemon());
        Thread thread1 = new ThreadTest1();
        Thread thread2 = new ThreadTest2();
        thread2.start();
        thread1.start();
        thread1.join();
        thread2.join();
        long endTime =System.currentTimeMillis();
        System.out.println("Cost:"+(endTime-startTime));
    }

    /**
     * Method two: using Runnable
     */
    @Test
    public void test2() throws InterruptedException {
        Thread t3=new Thread(new TestRunnable());
        t3.start();
        //Without this method,it will fewer than 2,000 numbers were printed
        t3.join();
    }

    public void test4(){

    }
}

/**
 * It is implemented through extending the class {@link Thread}
 */
class ThreadTest1 extends Thread {
    @Override
    public void run() {
        System.out.println("It is implemented through extending the class");
        for (int i = 0; i < 2000; i++) {
            System.out.println(i);
        }
    }
}

class ThreadTest2 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 2000; i++) {
            System.out.println("---------------");
        }
    }
}

/**
 * It is implemented through implementing the interface {@link Runnable}
 */
class TestRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("It is implemented through implementing the interface");
        for (int i = 0; i < 2000; i++) {
            System.out.println(i);
        }
    }
}