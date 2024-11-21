package id.co.cimbniaga.financialproductmanagement.dto;

import id.co.cimbniaga.financialproductmanagement.model.Category;
import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private double price;
    private boolean availability;
    private long stock;
    private Category category;
}
