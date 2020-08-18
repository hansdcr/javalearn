package com.hans.learn.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UnifyResponseVO {
    private int code;
    private String message;
    private String request;
    private Object data;

    public UnifyResponseVO(Object data){
        this.setData(data);
    }
}
