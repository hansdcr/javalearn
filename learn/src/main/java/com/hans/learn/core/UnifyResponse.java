package com.hans.learn.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UnifyResponse<T> {
    private int code;
    private String message;
    private String request;
    private T data;

    public UnifyResponse(T data){
        this.setData(data);
    }
}
