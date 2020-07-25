package com.company.abstraction.hero;

import com.company.abstraction.ISkill;

public class Camille implements ISkill {
    public void q() {
        System.out.println("Camille Q");
    }
    public void w() {
        System.out.println("Camille W");
    }
    public void e() {
        System.out.println("Camille E");
    }
    public void r() {
        System.out.println("Camille R");
    }
}
