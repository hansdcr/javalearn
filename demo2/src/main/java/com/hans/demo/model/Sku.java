package com.hans.demo.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hans.demo.util.GenericAndJson;
import com.hans.demo.util.ListAndJson;
import com.hans.demo.util.MapAndJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Sku extends BaseEntity {
    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;

//    @Convert(converter = MapAndJson.class)
//    private Map<String, Object> test;
//
//    @Convert(converter = ListAndJson.class)
//    private List<Object> specs;

    private String specs;

    private String code;
    private Long stock;
    private Long categoryId;
    private Long rootCategoryId;


    public List<Spec> getSpecs() {
        if(this.specs == null){
            return Collections.emptyList();
        }
//         return GenericAndJson.jsonToList(this.specs, new TypeReference<List<Spec>>() {
//        });

        return GenericAndJson.jsonToList(this.specs);
    }

    public void setSpecs(List<Spec> specs) {
        if(specs.isEmpty()){
            return;
        }

        this.specs = GenericAndJson.objectToJson(specs);
    }
}
