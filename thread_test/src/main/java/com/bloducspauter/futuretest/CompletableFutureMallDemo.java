package com.bloducspauter.futuretest;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
        Student student = new Student();
        //Chain Programming
        student.setId(1).setName("Zhangsan").setMajor("English");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(student::getName);
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

@Getter
class NetMall {
    private final String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }


    public double clcPrice(String productName) {
        try {
            //sleep 1s
            TimeUnit.MICROSECONDS.sleep(1000000);
        } catch (InterruptedException e) {
            System.out.printf("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }


}

/**
 * @author Bloduc Spauter
 */
final class NetMallTest {
    static List<NetMall> netMalls = new ArrayList<>();

    public static void main(String[] args) {
        netMalls.add(new NetMall("NetMall1"));
        netMalls.add(new NetMall("NetMall2"));
        netMalls.add(new NetMall("NetMall3"));
        long startTime = System.currentTimeMillis();
        //Insert your code
        List<String> list = getPrice(netMalls, "mysql");
        list.forEach(System.out::println);
        long endTime = System.currentTimeMillis();
        System.out.println("----cost time: " + (endTime - startTime) + "ms");
        //Method two
        System.out.println("--------------------------");
        long startTime1 = System.currentTimeMillis();
        //Insert your code
        List<String> list2 = getPriceByCompletableFuture(netMalls, "oracle");
        list2.forEach(System.out::println);
        long endTime1 = System.currentTimeMillis();
        System.out.println("----cost time: " + (endTime1 - startTime1) + "ms");
    }

    /**
     * search one by one
     *
     * @param productName the produce name
     * @return a double number
     */
    public static List<String> getPrice(List<NetMall> netMallList, String productName) {
        return netMallList
                .stream()
                .map(netMall -> String.format(productName + " in %s price is %.2f",
                        netMall.getNetMallName(),
                        netMall.clcPrice(productName)))
                .collect(Collectors.toList());
    }

    /**
     * together
     *
     * @param productName the produce name
     * @return a double number
     */
    public static List<String> getPriceByCompletableFuture(List<NetMall> netMallList, String productName) {
        return netMallList.stream()
                .map(netMall ->
                        CompletableFuture.supplyAsync(() ->
                                String.format(productName + " in %s is %.2f",
                                        netMall.getNetMallName(),
                                        netMall.clcPrice(productName)))
                )
                .collect(Collectors.toList())
                .stream()
                .map(stringCompletableFuture -> stringCompletableFuture.join())
                .collect(Collectors.toList());
    }
}