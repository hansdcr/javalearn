package com.hans.demo.dto;

import com.hans.demo.core.enumeration.LoginType;
import com.hans.demo.dto.validators.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenGetDTO {
    @NotBlank(message = "account账号不能为空")
    private String account;

    @TokenPassword(min=5, max=30,message="{token.password}")
    private String password;

    private LoginType type;
}
