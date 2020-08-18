package com.hans.learn.exception.http;

public class HttpException extends RuntimeException {
    public Integer code;
    public Integer httpStatusCode = 500;


    public Integer getCode() {
        return code;
    }
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
}
