package com.bloducspauter.futuretest;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * {@link Runnable#run()} don't need param and no return statement
 * <p>
 * {@link java.util.function.Function#apply(Object)} needs one param and has a return statement
 * <p>
 * {@link java.util.function.Consumer#accept(Object)} needs one param and no return statement
 * <p>
 * {@link Supplier#get()} don't need param but has a return statement
 * <p>
 * {@link java.util.function.BiConsumer#accept(Object, Object)} needs two params and no return statement
 *
 * @author Bloduc Spauter
 */
public class CompletableFutureMallDemo {
    public static void main(String[] args) {
        Student student=new Student();
        //Chain Programming
        student.setId(1).setName("Zhangsan").setMajor("English");
        CompletableFuture<String> completableFuture=CompletableFuture.supplyAsync(student::getName);
        //The difference between it and join() is whether compiler report an error when compiling
        System.out.println(completableFuture.join());
    }
}

@Data
@NoArgsConstructor
@Accessors(chain = true)
class Student {
    private Integer id;
    private String name;
    private String major;

}