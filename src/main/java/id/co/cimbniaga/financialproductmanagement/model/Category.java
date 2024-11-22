package id.co.cimbniaga.financialproductmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

}
