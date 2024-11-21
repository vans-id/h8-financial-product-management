package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.dto.ReportSummaryDTO;
import id.co.cimbniaga.financialproductmanagement.dto.SimplifiedReportDTO;
import id.co.cimbniaga.financialproductmanagement.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<SimplifiedReportDTO> getTopProducts(ReportSummaryDTO reportSummaryDTO) {
        // Extract the start_date and end_date from the DTO (received from the request body)
        LocalDate startDate = reportSummaryDTO.getStart_date();
        LocalDate endDate = reportSummaryDTO.getEnd_date();

        // Get the results from the repository with the start and end dates
        List<Object[]> results = reportRepository.getTopProducts(startDate, endDate);


        // List to hold the mapped DTO data
        List<SimplifiedReportDTO> simplifiedReportDTOS = new ArrayList<>();

        // Process each result and convert to SimplifiedReportDTO
        for (Object[] result : results) {
            String productName = (String) result[0];
            Long productCount = (Long) result[1];
            Double priceDouble = (Double) result[2];
            Long stock = (Long) result[3];

            // Convert the price from Double to BigDecimal
            BigDecimal price = BigDecimal.valueOf(priceDouble);

            // Create a new SimplifiedReportDTO with the extracted data (excluding start_date and end_date)
            SimplifiedReportDTO dto = new SimplifiedReportDTO();
            dto.setProductName(productName);
            dto.setProductCount(productCount);
            dto.setPrice(price);
            dto.setStock(stock);

            // Add the DTO to the list
            simplifiedReportDTOS.add(dto);
        }

        return simplifiedReportDTOS;
    }
}
