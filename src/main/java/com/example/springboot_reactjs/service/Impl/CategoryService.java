package com.example.springboot_reactjs.service.Impl;

import com.example.springboot_reactjs.dto.CategoryDTO;
import com.example.springboot_reactjs.entity.Category;
import com.example.springboot_reactjs.entity.Product;
import com.example.springboot_reactjs.exception.AppException;
import com.example.springboot_reactjs.exception.ErrorCode;
import com.example.springboot_reactjs.repositories.CategoryRepository;
import com.example.springboot_reactjs.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO saveCategory(Category category) {
        categoryRepository.save(category);
        return mapperDTO(category);
    }

    @Override
    public List<CategoryDTO> getCategoryNoPageable() {
        List<CategoryDTO> result = categoryRepository.findAll().stream()
                .map(category -> mapperDTO(category)).collect(Collectors.toList());
        return result;
    }

    @Override
    public CategoryDTO findCategoryByIdById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return mapperDTO(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryRepository.deleteById(id);
    }
    public CategoryDTO mapperDTO(Category category){
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        if(category.getProducts()!=null){
            categoryDTO.setProductNames(category.getProducts().stream()
                    .map(Product::getName).collect(Collectors.toList()));
        }

        return categoryDTO;
    }
}
