package id.co.cimbniaga.financialproductmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private double price;
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}