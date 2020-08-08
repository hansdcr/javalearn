package com.hans.demo.core.interceptors;

import com.hans.demo.dto.validators.TokenPasswordValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ScopeLevel {
    int value() default 4;
}
