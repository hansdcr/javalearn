package com.company.reflect;

import com.company.reflect.hero.Camille;
import com.company.reflect.hero.Diana;
import com.company.reflect.hero.Irelia;

public class HeroFactory {
    public static ISkill getHero(String name) throws Exception {
        ISkill iSkill;
        String classStr = "com.company.reflect.hero."+name;
        Class<?> cla = Class.forName(classStr);
        Object obj = cla.newInstance();
        return (ISkill)obj;
    }
}
