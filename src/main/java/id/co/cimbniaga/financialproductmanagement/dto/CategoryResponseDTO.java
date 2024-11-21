package id.co.cimbniaga.financialproductmanagement.dto;

import lombok.Data;

@Data
public class CategoryResponseDTO {
    private long id;
    private String name;

    public CategoryResponseDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }
}