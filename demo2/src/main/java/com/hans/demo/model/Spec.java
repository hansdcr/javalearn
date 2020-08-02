package com.hans.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Spec {
    // 为什么要定义Spec这个类，为什么不加Entity, 怎么帮助进行json序列化的
    private Long keyId;
    private String Key;
    private Long valueId;
    private String value;
}
