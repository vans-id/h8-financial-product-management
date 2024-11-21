package id.co.cimbniaga.financialproductmanagement.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
}
