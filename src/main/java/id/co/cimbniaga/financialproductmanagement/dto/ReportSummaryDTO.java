package id.co.cimbniaga.financialproductmanagement.dto;

import java.math.BigDecimal;

public class ReportSummaryDTO {

    private String email;
    private String productName;
    private Long productCount;
    private BigDecimal price;

    // Constructor
    public ReportSummaryDTO(String email, String productName, Long productCount, BigDecimal price) {
        this.email = email;
        this.productName = productName;
        this.productCount = productCount;
        this.price = price;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductCount() {
        return productCount;
    }

    public void setProductCount(Long productCount) {
        this.productCount = productCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
