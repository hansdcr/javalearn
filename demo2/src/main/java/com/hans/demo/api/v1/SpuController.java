package com.hans.demo.api.v1;

import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.hans.demo.bo.PageCounter;
import com.hans.demo.exception.http.NotFoundException;
import com.hans.demo.model.Spu;
import com.hans.demo.service.SpuService;
import com.hans.demo.util.CommonUtil;
import com.hans.demo.vo.PagingDozer;
import com.hans.demo.vo.SpuSimplifyVO;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;


@Validated
@RestController
@RequestMapping("/spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    @GetMapping("/id/{id}/detail")
    public Spu getDetail(@PathVariable @Positive Long id){
        Spu spu = spuService.getSpu(id);
        if (spu == null) {
            throw new NotFoundException(30003);
        }
        return spu;
    }

    @GetMapping("/id/{id}/simplify")
    public SpuSimplifyVO getSimplifySpu(@PathVariable @Positive(message = "{id.positive}") Long id){
        Spu spu = spuService.getSpu(id);
        SpuSimplifyVO vo = new SpuSimplifyVO();
        BeanUtils.copyProperties(spu, vo);
        return vo;
    }

//    @GetMapping("/latest")
//    public List<Spu> getLatestSpuList(){
//        return spuService.getLatestPagingSpu();
//    }

    @GetMapping("/latest")
    public PagingDozer<Spu,SpuSimplifyVO> getLatestSpuList(
            @PathVariable @Positive Long id,
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count){

        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = this.spuService.getLatestPagingSpu(pageCounter.getPage(), pageCounter.getCount());
        return new PagingDozer<Spu, SpuSimplifyVO>(page, SpuSimplifyVO.class);
    }

    @GetMapping("/by/category/{id}")
    public PagingDozer<Spu,SpuSimplifyVO> getCategoryById(
            @PathVariable @Positive Long id,
            @RequestParam(name = "is_root",defaultValue = "false") Boolean isRoot,
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count
    ){
        // 分页的类型转换
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        // 数据库查询
        Page<Spu> page = this.spuService.getByCategory(id, isRoot, pageCounter.getPage(), pageCounter.getCount());
        // 对象copy
        return new PagingDozer<Spu, SpuSimplifyVO>(page,SpuSimplifyVO.class);
    }
}
