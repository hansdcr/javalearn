package com.hans.demo.vo;

import com.hans.demo.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoriesAllVO {
    // List中放入的是模型精简之后的字段
    private List<CategoryPureVO> roots;
    private List<CategoryPureVO> subs;

    public CategoriesAllVO(Map<Integer, List<Category>> map){
        this.roots = map.get(1).stream().map(CategoryPureVO::new).collect(Collectors.toList());
        this.subs = map.get(2).stream().map(CategoryPureVO::new).collect(Collectors.toList());
    }
}
