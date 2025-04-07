package com.example.springboot_reactjs.service.Impl;

import com.example.springboot_reactjs.dto.CategoryDTO;
import com.example.springboot_reactjs.entity.Category;
import com.example.springboot_reactjs.entity.Product;
import com.example.springboot_reactjs.exception.AppException;
import com.example.springboot_reactjs.exception.ErrorCode;
import com.example.springboot_reactjs.repositories.CategoryRepository;
import com.example.springboot_reactjs.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize ;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    @PreAuthorize ("hasAuthority('ADMIN')")
    public CategoryDTO saveCategory(Category category) {
        categoryRepository.save(category);
        return mapperDTO(category);
    }

    @Override
    @PreAuthorize ("hasAuthority('ADMIN')")
    public List<CategoryDTO> getCategoryNoPageable() {
        List<CategoryDTO> result = categoryRepository.findAll().stream()
                .map(category -> mapperDTO(category)).collect(Collectors.toList());
        return result;
    }

    @Override
    @PreAuthorize ("hasAuthority('ADMIN')")
    public CategoryDTO findCategoryByIdById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return mapperDTO(category);
    }

    @Override
    @PreAuthorize ("hasAuthority('ADMIN')")
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
