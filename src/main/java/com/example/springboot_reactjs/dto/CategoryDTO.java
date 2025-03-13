package com.example.springboot_reactjs.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private List<String> productNames;

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }
}
