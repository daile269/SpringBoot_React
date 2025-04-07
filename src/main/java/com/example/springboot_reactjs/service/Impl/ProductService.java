package com.example.springboot_reactjs.service.Impl;

import com.example.springboot_reactjs.dto.ProductDTO;
import com.example.springboot_reactjs.entity.Category;
import com.example.springboot_reactjs.entity.Product;
import com.example.springboot_reactjs.exception.AppException;
import com.example.springboot_reactjs.exception.ErrorCode;
import com.example.springboot_reactjs.repositories.CategoryRepository;
import com.example.springboot_reactjs.repositories.ProductRepository;
import com.example.springboot_reactjs.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;


    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO save(Product product) {
        categoryRepository.findById(product.getCategoryId())
                .orElseThrow((() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)));
        productRepository.save(product);
        return mapToProductDTO(product);
    }

    @Override
    public List<ProductDTO> getListProductNoPageable() {
        List<ProductDTO> result = productRepository.findAll().stream()
                .map(product -> mapToProductDTO(product)).collect(Collectors.toList());
        return result;
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return mapToProductDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getProductPageable(Pageable pageable) {
        List<ProductDTO> result = productRepository.findAll(pageable).getContent()
                .stream().map(product -> mapToProductDTO(product)).collect(Collectors.toList());
        return result;
    }

    @Override
    public ProductDTO updateCategoryOfProduct(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setCategoryId(categoryId);
        productRepository.save(product);
        return mapToProductDTO(product);
    }

    @Override
    public ProductDTO addProductWithCategory(Product product,Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        product.setCategoryId(categoryId);
        return mapToProductDTO(productRepository.save(product));
    }

    @Override
    public List<ProductDTO> getProductByCategoryPageable(Pageable pageable,Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        List<ProductDTO> result = productRepository.getProductByCategory(categoryId,pageable).stream()
                .map(product -> mapToProductDTO(product)).collect(Collectors.toList());
        return result;
    }

    private ProductDTO mapToProductDTO(Product product){
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        if(product.getCategory()!=null){
            productDTO.setCategoryName(product.getCategory().getName());
        }
        return productDTO;
    }
}
