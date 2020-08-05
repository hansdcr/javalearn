package com.hans.demo.repository;

import com.hans.demo.model.GridCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GridCategoryRepository extends JpaRepository<GridCategory, Long> {
    @Override
    List<GridCategory> findAll();
}
