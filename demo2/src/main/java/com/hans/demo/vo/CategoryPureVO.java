package com.hans.demo.vo;

import com.hans.demo.model.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;

@Getter
@Setter
public class CategoryPureVO {
    // CategoryPureVO作用是精简一部分Category模型中的字段
    private Long id;
    private String name;
    private Boolean isRoot;
    private Long parentId;
    private String img;
    private Long index;

    public CategoryPureVO(Category category) {
        BeanUtils.copyProperties(category ,this);
    }
}
