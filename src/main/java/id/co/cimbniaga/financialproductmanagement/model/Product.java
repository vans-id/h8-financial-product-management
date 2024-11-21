package id.co.cimbniaga.financialproductmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}