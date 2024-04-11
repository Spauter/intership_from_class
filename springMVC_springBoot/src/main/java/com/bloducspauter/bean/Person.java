package com.bloducspauter.bean;

import lombok.Data;

import java.util.List;

@Data
public class Person {

    String name;
    int age;
    boolean student;
    Person father;
    Person mather;
    List<Person> friends;

}
