package com.bloducspauter.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 * <p>
 * 是指多个线程按照中请锁的顺序来获取锁，这里类似排队买票，先来的人先买后来的人在队尾排着，这是公平的
 * Lock lock=new ReentrantLock(true);/true 表示公平锁,先来先得
 * </p>
 * 非公平锁
 * <p>
 * 是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先事请的线程优先获取锁，在高并
 * 发环境下，有可能造成优先级翻转或者饥饿的状态(某个线程一直得不到锁)
 * Lock lock=new ReentrantLock(false);//false 表示非公平锁，后来的也可能先获得锁
 * Lock lock =new ReentrantLock();//默认非公平锁
 * </p>
 * <p>
 * 恢复挂起的线程到真正锁的获取还是有时间差的，从开发人员来看这个时间微乎其微，
 * 但是从CPU的角度来看，这个时间差存在的还是很明显的。
 * 所以
 * 非公平锁能更充分的利用CPU 的时间片，尽量减少CPU空闲状态时间。
 * </p>
 * <p>
 * 使用多线程很重要的考量点是线程切换的开销，当采用非公平锁时，当1个线程请求锁获取同步状态，然后释放同步状态，
 * 所以刚释放锁的线程在此
 * 刻再次获取同步状态的概率就变得非常大，所以就减少了线程的开销。
 * </p>
 *
 * @author Bloduc Spauter
 */
public class LockDemoTest2 {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "a").start();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "b").start();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "c").start();
    }
}

class Ticket {
    private int number = 50;
    ReentrantLock lock = new ReentrantLock(
//            true
    );

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + " sales " + number + " ticket ,remaining " + number--);
            }
        } finally {
            lock.unlock();
        }
    }
}