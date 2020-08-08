package com.hans.demo.dto;

import com.hans.demo.dto.validators.PasswordEqual;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Builder
@Getter
@Setter
@PasswordEqual(min = 1)
public class PersonDTO {
    @Length(min=2, max=10, message = "name长度错误")
    private String name;
    private Integer age;

    private String password1;
    private String password2;
}
