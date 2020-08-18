package com.hans.learn.core;

import com.hans.learn.core.configuration.ExceptionCodeConfiguration;
import com.hans.learn.exception.http.HttpException;
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
import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionAdvise{
    @Autowired
    private ExceptionCodeConfiguration exceptionCodeConfiguration;

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public UnifyResponse handleException(HttpServletRequest req, Exception e){
        String method = req.getMethod();
        String requestUri = req.getRequestURI();
        String request = method + " " + requestUri;

        System.out.println(e);

        String message = exceptionCodeConfiguration.getMessage(9999);
        UnifyResponse unifyResponse = new UnifyResponse(9999, message, request, null);
        return unifyResponse;
    }

    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest req, HttpException e){
        String method = req.getMethod();
        String requestUri = req.getRequestURI();
        String request = method + " " + requestUri;

        // 通过ResponseEntity设置http响应头信息，比如状态码
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());

        String message = exceptionCodeConfiguration.getMessage(e.getCode());
        UnifyResponse unifyResponse = new UnifyResponse(e.getCode(), message, request, null);
        ResponseEntity<UnifyResponse> r = new ResponseEntity<>(unifyResponse, headers, httpStatus);

        return r;
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleConstraintException(HttpServletRequest req, ConstraintViolationException e){
        String method = req.getMethod();
        String requestUri = req.getRequestURI();
        String request = method + " " + requestUri;


        return new UnifyResponse(40000, e.getMessage(), request,null);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public UnifyResponse handleBeanValidation(HttpServletRequest req, MethodArgumentNotValidException e) {
        String method = req.getMethod();
        String requestUri = req.getRequestURI();
        String request = method + " " + requestUri;

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);
        return new UnifyResponse(40001, message, request, null);
    }

    public String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error -> errorMsg.append(error.getDefaultMessage()).append(";"));
        return errorMsg.toString();
    }
}
