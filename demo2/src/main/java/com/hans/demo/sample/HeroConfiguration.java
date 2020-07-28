package com.hans.demo.sample;

import com.hans.demo.sample.condtion.DianaCondition;
import com.hans.demo.sample.hero.Comille;
import com.hans.demo.sample.hero.Diana;
import com.hans.demo.sample.hero.Irelia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

@Configuration
public class HeroConfiguration {
//    @Bean
//    public ISkill camille() {
//        return new Comille("Comille", 18);
//    }

    @Primary
    @Bean
    public ISkill irelia() {
        return new Irelia();
    }

    @Bean
    @Conditional(DianaCondition.class)
    public ISkill diana() {
        return new Diana();
    }
}
