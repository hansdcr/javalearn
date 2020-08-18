package com.hans.learn.dto;

import com.hans.learn.dto.validators.BannerPost;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Getter
@Setter
@BannerPost
public class BannerDTO {
    private String name;

    private String title;
}
