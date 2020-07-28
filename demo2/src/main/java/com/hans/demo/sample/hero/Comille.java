package com.hans.demo.sample.hero;

import com.hans.demo.sample.ISkill;

public class Comille implements ISkill {
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private String name;
    private Integer age;

    public Comille(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Comille() {
        System.out.println("hello Comille, i am hans!");
    }
    public void q() {
        System.out.println("Comille Q");
    }
    public void w() {
        System.out.println("Comille W");
    }
    public void e() {
        System.out.println("Comille E");
    }
    public void r() {
        System.out.println("Comille R");
    }
}
