package id.co.cimbniaga.financialproductmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SimplifiedReportDTO {
    private String email;
    private String productName;
    private Long productCount;
    private BigDecimal price;
}
