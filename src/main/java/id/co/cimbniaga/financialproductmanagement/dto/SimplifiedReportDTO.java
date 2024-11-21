package id.co.cimbniaga.financialproductmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class SimplifiedReportDTO {
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_count")
    private Long productCount;
    private Double price;
    private Long stock;
}
