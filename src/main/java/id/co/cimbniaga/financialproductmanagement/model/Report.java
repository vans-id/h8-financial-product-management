package id.co.cimbniaga.financialproductmanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("activity_type")
    private String activityType;
    private Timestamp timestamp;
    private String details;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
