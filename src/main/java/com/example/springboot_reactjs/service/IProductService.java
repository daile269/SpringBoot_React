package com.example.springboot_reactjs.service;

import com.example.springboot_reactjs.dto.ProductDTO;
import com.example.springboot_reactjs.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    ProductDTO save(Product product);

    List<ProductDTO> getListProductNoPageable();

    ProductDTO getProductById(Long id);

    void deleteProduct(Long id);

    List<ProductDTO> getProductPageable(Pageable pageable);

    ProductDTO updateCategoryOfProduct(Long productId, Long categoryId);

    ProductDTO addProductWithCategory(Product product,Long categoryId);

    List<ProductDTO> getProductByCategoryPageable(Pageable pageable,Long categoryId);
}
