package com.hans.learn.exception.http;

public class NotFoundException extends HttpException {
    public NotFoundException(Integer code){
        this.code = code;
        this.httpStatusCode = 404;
    }
}
