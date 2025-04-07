package com.example.springboot_reactjs.controller.admin;

import com.example.springboot_reactjs.dto.CategoryDTO;
import com.example.springboot_reactjs.dto.ProductDTO;
import com.example.springboot_reactjs.dto.response.ApiResponse;
import com.example.springboot_reactjs.entity.Category;
import com.example.springboot_reactjs.entity.Product;
import com.example.springboot_reactjs.service.ICategoryService;
import com.example.springboot_reactjs.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {

    private final ICategoryService categoryService;
    private final IProductService productService;

    @PostMapping
    @ResponseBody
    public ApiResponse<CategoryDTO> createCategory(@RequestBody Category category){

        return ApiResponse.<CategoryDTO>builder()
                .code(200)
                .result(categoryService.saveCategory(category))
                .message("Thêm thành công")
                .build();
    }

    @GetMapping
    @ResponseBody
    public List<CategoryDTO> getListCategory(){
        return categoryService.getCategoryNoPageable();
    }

    @GetMapping(value = "/{categoryId}")
    @ResponseBody
    public CategoryDTO findCategoryById(@PathVariable Long categoryId){
        return categoryService.findCategoryByIdById(categoryId);
    }

    @PutMapping(value = "/{categoryId}")
    @ResponseBody
    public ApiResponse<CategoryDTO> updateCategory(@RequestBody Category category, @PathVariable Long categoryId){
        category.setId(categoryId);
        return ApiResponse.<CategoryDTO>builder()
                .code(200)
                .result(categoryService.saveCategory(category))
                .message("Cập nhật thành công")
                .build();
    }

    @DeleteMapping(value = "/{categoryId}")
    @ResponseBody
    public List<CategoryDTO> deleteCategoryById(@PathVariable Long categoryId){
        categoryService.deleteCategoryById(categoryId);
        return categoryService.getCategoryNoPageable();
    }

    @PostMapping(value = "/{categoryId}/products")
    @ResponseBody
    public ApiResponse<ProductDTO> addProductWithCategory(@RequestBody Product product,
            @PathVariable Long categoryId){
        return ApiResponse.<ProductDTO>builder()
                .code(200)
                .result(productService.addProductWithCategory(product,categoryId))
                .message("Thêm thành công")
                .build();
    }

    @GetMapping(value = "/{categoryId}/products")
    @ResponseBody
    public List<ProductDTO> getProductByCategory(@PathVariable Long categoryId,
                                                 @RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return productService.getProductByCategoryPageable(pageable,categoryId);
    }
}
