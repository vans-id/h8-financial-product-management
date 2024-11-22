package id.co.cimbniaga.financialproductmanagement.dto;

import id.co.cimbniaga.financialproductmanagement.model.Category;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private double price;
    @Column(name = "isAvailable")
    private boolean availability;
    private long stock;
    private Category category;
}
