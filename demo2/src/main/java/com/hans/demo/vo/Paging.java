package com.hans.demo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Paging<T> {
    private Long total; // 总的数据条数
    private Integer count; // 每次请求返回的数据量，比如10条
    private Integer page; // 当前的页码
    private Integer totalPage; // 总的页数
    private List<T> items; // 真正的数据内容

    public Paging(Page<T> pageT){
        this.initPageParameters(pageT);
        this.items = pageT.getContent();
    }

    void initPageParameters(Page<T> pageT) {
        this.total = pageT.getTotalElements();
        this.count = pageT.getSize();
        this.page = pageT.getNumber();
        this.totalPage = pageT.getTotalPages();
    }
}
