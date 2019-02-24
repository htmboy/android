package com.aooled_laptop.intent;

import java.io.Serializable;

public class Person implements Serializable {

    private int age;
    private String name;
    private String address;

    public Person(int age, String name, String address){
        this.address = address;
        this.name = name;
        this.age = age;
    }

    public String toString(){
        return "[name=]" + name + " age=" + age + " address=" + address + "]";
    }
}

