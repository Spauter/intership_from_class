package com.bloducspauter;

import com.bloducspauter.excel.BsExcelUtil;
import com.bloducspauter.future.CompletableFutureDemoTest;
import com.bloducspauter.future.CompletableFutureMallDemo;
import com.bloducspauter.future.FutureDemo;
import com.bloducspauter.lock.LockDemoTest;
import com.bloducspauter.lock.LockDemoTest2;
import com.bloducspauter.thread.PersonalTest;

import java.io.IOException;

/**
 * @author Bloduc Spauter
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
        //sixth
        System.out.println(LockDemoTest2.class.getTypeName());
        BsExcelUtil<Object> bsExcelUtil=new BsExcelUtil<>(Main.class);
        try {
            bsExcelUtil.readToList("D:\\upload\\Teacher.xlsx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
