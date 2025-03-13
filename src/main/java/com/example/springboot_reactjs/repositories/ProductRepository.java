package com.example.springboot_reactjs.repositories;

import com.example.springboot_reactjs.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "SELECT * FROM product where category_id =:categoryId", nativeQuery = true)
    List<Product> getProductByCategory(Long categoryId, Pageable pageable);
}
