package com.example.hibernatesearchexample.product;

import lombok.Value;

@Value
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private int productionYear;

    public Product toEntity() {
        return new Product(id, name, description, productionYear);
    }
}
