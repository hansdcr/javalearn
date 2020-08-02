package com.hans.demo.exception.http;

public class ServerErrorException extends HttpException{
    public ServerErrorException(int code) {
        this.httpStatusCode = 404;
        this.code = code;
    }
}
