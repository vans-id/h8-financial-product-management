package id.co.cimbniaga.financialproductmanagement.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportSummaryDTO {

    private String email;
    private String productName;
    private Long productCount;
    private Double price;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;

}
