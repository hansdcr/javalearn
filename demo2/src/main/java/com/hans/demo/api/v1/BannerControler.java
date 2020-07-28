package com.hans.demo.api.v1;

import com.hans.demo.exception.http.NotFoundException;
import com.hans.demo.sample.ISkill;
import com.hans.demo.sample.hero.Diana;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/banner")
public class BannerControler {
    @Autowired
    private ISkill iSkill;

    @GetMapping("/test")
    public String test() {
        iSkill.r();

        throw new NotFoundException(10000);
//        return "hello,world3!";
    }
}
