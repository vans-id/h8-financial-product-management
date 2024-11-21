package id.co.cimbniaga.financialproductmanagement.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private long id;
    private String name;
    private String description;
    private double price;
    private boolean availability;
    private long stock;
    private CategoryResponseDTO category;

    public ProductResponseDTO(long id, String name, String description, double price, boolean availability, long stock, CategoryResponseDTO category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.stock = stock;
        this.category = category;
    }
}

