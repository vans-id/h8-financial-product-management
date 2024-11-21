package id.co.cimbniaga.financialproductmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SimplifiedReportDTO {
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_count")
    private Long productCount;
    private BigDecimal price;
    private Long stock;
}
