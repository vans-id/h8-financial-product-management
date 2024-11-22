package id.co.cimbniaga.financialproductmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity(name = "reports")
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "message_id")
    private Messages messages;
    private Timestamp timestamp;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
