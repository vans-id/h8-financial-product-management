package id.co.cimbniaga.financialproductmanagement.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private long id;
    private String name;
    private String description;
    private double price;
    private boolean availability;
    private CategoryResponseDTO category;

    public ProductResponseDTO(long id, String name, String description, double price, boolean availability, CategoryResponseDTO category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.category = category;
    }
}

