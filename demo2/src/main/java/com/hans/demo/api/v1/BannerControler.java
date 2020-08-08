package com.hans.demo.api.v1;

import com.hans.demo.core.interceptors.ScopeLevel;
import com.hans.demo.dto.PersonDTO;
import com.hans.demo.exception.http.ForbiddenException;
import com.hans.demo.exception.http.NotFoundException;
import com.hans.demo.model.Banner;
import com.hans.demo.sample.ISkill;
import com.hans.demo.sample.hero.Diana;
import com.hans.demo.service.BannerService;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;


@Validated
@RestController
@RequestMapping("/banner")
public class BannerControler {
    @Autowired
    private BannerService bannerService;

    @ScopeLevel
    @GetMapping("/name/{name}")
    public Banner getByName(@PathVariable @NotBlank String name){
        Banner banner =  bannerService.getByName(name);
        if(banner == null) {
            throw new NotFoundException(30005);
        }
        return banner;
    }


    @GetMapping("/test")
    public String test() {
        System.out.println("hahah");
        throw new NotFoundException(10000);
    }

    @PostMapping("/test2/{id}")
    public PersonDTO test2(
            @PathVariable @Range(min = 1, max = 10, message = "长度错误") Integer id,
            @RequestParam @Length(min = 8) String name,
            @RequestBody @Validated PersonDTO person) {
        PersonDTO dto = PersonDTO.builder().name("7").age(18).build();
        return dto;
    }
}
