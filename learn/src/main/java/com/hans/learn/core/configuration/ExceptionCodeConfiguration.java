package com.hans.learn.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "learn")
@PropertySource(value = "classpath:config/exception-code.properties")
@Component
public class ExceptionCodeConfiguration {
    private Map<Integer, String> codes = new HashMap<>();

    public Map<Integer, String> getCodes(){
        return this.codes;
    }

    public void setCodes(Map<Integer,String> codes){
        this.codes = codes;
    }

    public String getMessage(int code){
        return this.codes.get(code);
    }
}
