package com.hans.demo.core;

import com.hans.demo.core.configuration.ExceptionCodeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.ws.http.HTTPException;

import com.hans.demo.exception.http.HttpException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @Autowired
    private ExceptionCodeConfiguration codeConfiguration;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest req, Exception e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        System.out.println(e);
        UnifyResponse message = new UnifyResponse(9999, "测试未知异常", method + " " + requestUrl);
        return message;

    }

    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity handleHttpException(HttpServletRequest req, HttpException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());

        UnifyResponse message = new UnifyResponse(e.getCode(), codeConfiguration.getMessage(e.getCode()), method + " " + requestUrl);
        ResponseEntity<UnifyResponse> r = new ResponseEntity<>(message, headers, httpStatus);
        return r;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public UnifyResponse handleBeanValidation(HttpServletRequest req, MethodArgumentNotValidException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);
        return new UnifyResponse(10001, message, method + " " + requestUrl);
    }

    public String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error -> errorMsg.append(error.getDefaultMessage()).append(";"));
        return errorMsg.toString();
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleConstraintException(HttpServletRequest req, ConstraintViolationException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        String message = e.getMessage();

        return new UnifyResponse(10001, message, method + " " + requestUrl);
//        for(ConstraintViolation error: e.getConstraintViolations()){
//            ConstraintViolation a = error;
//        }
//        return null;
    }
}
