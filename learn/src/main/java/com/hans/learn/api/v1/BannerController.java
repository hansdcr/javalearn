package com.hans.learn.api.v1;

import com.hans.learn.core.GlobalExceptionAdvise;
import com.hans.learn.core.configuration.ExceptionCodeConfiguration;
import com.hans.learn.dto.BannerDTO;
import com.hans.learn.dto.validators.NameLength;
import com.hans.learn.exception.http.HttpException;
import com.hans.learn.exception.http.NotFoundException;
import com.hans.learn.model.Banner;
import com.hans.learn.vo.UnifyResponseVO;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping("/v1/banner")
public class BannerController {
    @GetMapping("/name/{name}")
    public Banner test(@PathVariable @NameLength(min = 3, max = 9) String name){
        Banner banner = new Banner(1,"b1-name","b1-desc","b1-title","b1-img");
        //Banner banner = new Banner();
        return banner;
    }

    @PostMapping("/name")
    public Banner test(@RequestBody @Validated BannerDTO bannerDTO){
        Banner banner = new Banner(1,"b1-name","b1-desc","b1-title","b1-img");
        return banner;
    }
}