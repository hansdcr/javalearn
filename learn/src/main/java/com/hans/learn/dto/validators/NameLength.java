package com.hans.learn.dto.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = NameLengthValidator.class)
public @interface NameLength {
    int min() default 3;
    int max() default 10;
    String message() default "名字长度需要大于3小于10";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
