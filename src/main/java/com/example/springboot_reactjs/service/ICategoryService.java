package com.example.springboot_reactjs.service;

import com.example.springboot_reactjs.dto.CategoryDTO;
import com.example.springboot_reactjs.entity.Category;

import java.util.List;

public interface ICategoryService {
    CategoryDTO saveCategory(Category category);

    List<CategoryDTO> getCategoryNoPageable();

    CategoryDTO findCategoryByIdById(Long id);

    void deleteCategoryById (Long id);


}
