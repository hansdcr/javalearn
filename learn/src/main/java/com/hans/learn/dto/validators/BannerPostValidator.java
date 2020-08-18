package com.hans.learn.dto.validators;

import com.hans.learn.dto.BannerDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BannerPostValidator implements ConstraintValidator<BannerPost, BannerDTO> {
    private int min;
    private int max;

    @Override
    public void initialize(BannerPost constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(BannerDTO bannerDTO, ConstraintValidatorContext constraintValidatorContext) {
        return bannerDTO.getTitle().length() >= this.min && bannerDTO.getTitle().length() >= this.max;
    }
}
