package com.hans.demo.service;

import com.hans.demo.model.GridCategory;
import com.hans.demo.repository.GridCategoryRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GridCategoryService {
    @Autowired
    private GridCategoryRepository gridCategoryRepository;

    public List<GridCategory> getGridCategories(){
        return gridCategoryRepository.findAll();
    }
}
