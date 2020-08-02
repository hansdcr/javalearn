package com.hans.demo.repository;

import com.hans.demo.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpuRepository extends JpaRepository<Spu, Long> {
    // JpaRepository<Spu, Long> Spu实体的类名，Long实体的主键
    Spu findOneById(Long id);

    Page<Spu> findAll(Pageable pageable);

    Page<Spu> findByCategoryIdOrderByCreateTimeDesc(Long cid, Pageable pageable);

    Page<Spu> findByRootCategoryIdOrderByCreateTimeDesc(Long cid, Pageable pageable);
}
