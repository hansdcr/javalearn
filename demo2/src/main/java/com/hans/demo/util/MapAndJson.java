package com.hans.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hans.demo.exception.http.NotFoundException;
import com.hans.demo.exception.http.ServerErrorException;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

@Converter
public class MapAndJson implements AttributeConverter<Map<String, Object>, String> {
    // 实现接口AttributeConverter，让JPA自动调用实现序列化和反序列化
    // 第一个参数Model中类型Map<String, Object>
    // 第二个参数String映射到数据库的类型

    @Autowired
    private ObjectMapper mapper;

    @Override
    @SuppressWarnings("unchecked")
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        // 序列化
        try {
            return mapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> convertToEntityAttribute(String s) {
        // 反序列化
        try {
            // 这里为什么使用的是HashMap而不是Map
            if (s==null){return null;}
            Map<String, Object> t = mapper.readValue(s, HashMap.class);
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }
}
