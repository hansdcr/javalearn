package com.hans.demo.api.v1;

import com.hans.demo.exception.http.NotFoundException;
import com.hans.demo.model.Category;
import com.hans.demo.model.GridCategory;
import com.hans.demo.service.CategoryService;
import com.hans.demo.service.GridCategoryService;
import com.hans.demo.vo.CategoriesAllVO;
import com.hans.demo.vo.CategoryPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Validated
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GridCategoryService gridCategoryService;

    @GetMapping("/all")
    public CategoriesAllVO getaAll(){
        Map<Integer, List<Category>> categories = categoryService.getAll();
        return new CategoriesAllVO(categories);
    }

    @GetMapping("/grid/all")
    public List<GridCategory> getGridCategoryList(){
        List<GridCategory> gridCategories =  gridCategoryService.getGridCategories();
        if(gridCategories.isEmpty()){
            throw  new NotFoundException(30000);
        }

        return gridCategories;
    }
}
