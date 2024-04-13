package com.bloducspauter;

import com.bloducspauter.future.CompletableFutureDemoTest;
import com.bloducspauter.future.CompletableFutureMallDemo;
import com.bloducspauter.future.FutureDemo;
import com.bloducspauter.lock.LockDemoTest;
import com.bloducspauter.thread.PersonalTest;

/**
 * @author Bloduc Spauter
 *
 */
public class Main {
    public static void main(String[] args) {
        //first
        System.out.println(PersonalTest.class.getTypeName());
        //second
        System.out.println(FutureDemo.class.getTypeName());
        //third
        System.out.println(CompletableFutureMallDemo.class.getTypeName());
        //fourth
        System.out.println(CompletableFutureDemoTest.class.getTypeName());
        //fifth
        System.out.println(LockDemoTest.class.getTypeName());
    }
}
