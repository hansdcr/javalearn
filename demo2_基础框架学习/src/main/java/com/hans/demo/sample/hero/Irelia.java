package com.hans.demo.sample.hero;

import com.hans.demo.sample.ISkill;

public class Irelia implements ISkill {
    public Irelia() {
        System.out.println("hello Irelia, i am hans!");
    }
    public void q() {
        System.out.println("Irelia Q");
    }
    public void w() {
        System.out.println("Irelia W");
    }
    public void e() {
        System.out.println("Irelia E");
    }
    public void r() {
        System.out.println("Irelia R");
    }
}
