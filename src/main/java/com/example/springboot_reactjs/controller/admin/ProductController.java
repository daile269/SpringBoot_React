package com.example.springboot_reactjs.controller.admin;

import com.example.springboot_reactjs.dto.ProductDTO;
import com.example.springboot_reactjs.dto.response.ApiResponse;
import com.example.springboot_reactjs.entity.Product;
import com.example.springboot_reactjs.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping
    @ResponseBody
    public ApiResponse<ProductDTO> createProduct(@RequestBody Product product){
        return ApiResponse.<ProductDTO>builder()
                .code(200)
                .message("Thêm mới sản phẩm thành công")
                .result(productService.save(product))
                .build();
    }

    @GetMapping
    @ResponseBody
    public List<ProductDTO> getAllProduct(){
        return productService.getListProductNoPageable();
    }

    @GetMapping(value = "/{productId}")
    @ResponseBody
    public ProductDTO getProductById(@PathVariable Long productId){
        return productService.getProductById(productId);
    }

    @PutMapping(value = "/{productId}")
    @ResponseBody
    public ApiResponse<ProductDTO> updateProduct(@RequestBody Product product,
                                    @PathVariable Long productId){
        product.setId(productId);

        return ApiResponse.<ProductDTO>builder()
                .code(200)
                .message("Cập nhật sản phẩm thành công")
                .result(productService.save(product))
                .build();
    }

    @DeleteMapping(value = "{productId}")
    @ResponseBody
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Xoa thành công");
    }

    @GetMapping(value = "/paginated")
    @ResponseBody
    public List<ProductDTO> getProductPaginated(@RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return productService.getProductPageable(pageable);

    }

    @PatchMapping(value ="/{productId}/category/{categoryId}")
    @ResponseBody
    public ApiResponse<ProductDTO> updateCategoryOfProduct(@PathVariable Long productId, @PathVariable Long categoryId){
        return ApiResponse.<ProductDTO>builder()
                .code(200)
                .message("Cập nhật sản phẩm thành công")
                .result(productService.updateCategoryOfProduct(productId,categoryId))
                .build();
    }

}
