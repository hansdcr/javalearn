package com.hans.demo.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class SpuSimplifyVO {
    @Id
    private Long id;
    private String title;
    private String subtitle;
    private String price;
    private Long sketchSpecId;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private String forThemeImg;
}
