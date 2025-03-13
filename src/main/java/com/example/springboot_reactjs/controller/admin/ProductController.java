package com.example.springboot_reactjs.controller.admin;

import com.example.springboot_reactjs.dto.ProductDTO;
import com.example.springboot_reactjs.entity.Product;
import com.example.springboot_reactjs.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ProductDTO createProduct(@RequestBody Product product){
        return productService.save(product);
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
    public ProductDTO updateProduct(@RequestBody Product product,
                                    @PathVariable Long productId){
        product.setId(productId);
        return productService.save(product);
    }

    @DeleteMapping(value = "{productId}")
    @ResponseBody
    public List<ProductDTO> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return productService.getListProductNoPageable();
    }

    @GetMapping(value = "/paginated")
    @ResponseBody
    public List<ProductDTO> getProductPaginated(@RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return productService.getProductPageable(pageable);

    }

    @PatchMapping(value ="/{productId}/category/{categoryId}")
    @ResponseBody
    public ProductDTO updateCategoryOfProduct(@PathVariable Long productId, @PathVariable Long categoryId){
        return productService.updateCategoryOfProduct(productId,categoryId);
    }

}
