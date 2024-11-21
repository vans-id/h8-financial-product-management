package id.co.cimbniaga.financialproductmanagement.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReportSummaryDTO {

    private String email;
    private String productName;
    private Long productCount;
    private BigDecimal price;
    @JsonIgnore
    private LocalDate start_date;
    @JsonIgnore
    private LocalDate end_date;

}
