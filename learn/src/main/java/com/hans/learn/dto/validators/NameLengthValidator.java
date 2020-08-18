package com.hans.learn.dto.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameLengthValidator implements ConstraintValidator<NameLength,String> {
    private Integer min;
    private Integer max;

    @Override
    public void initialize(NameLength constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() <= this.min && s.length() >= this.max;
    }
}
