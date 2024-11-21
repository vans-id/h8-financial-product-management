package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.dto.ReportSummaryDTO;
import id.co.cimbniaga.financialproductmanagement.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<ReportSummaryDTO> getTopProducts() {
        // Get the results from the repository
        List<Object[]> results = reportRepository.getTopProducts();

        // List to hold the mapped DTO data
        List<ReportSummaryDTO> reportSummaryDTOs = new ArrayList<>();

        // Process each result and convert to DTO
        for (Object[] result : results) {
            String email = (String) result[0];  // Email from the user table
            String productName = (String) result[1];  // Product name from the product table
            Long productCount = (Long) result[2];  // Count of the product
            Double priceDouble = (Double) result[3];  // Price from the product table

            // Convert the price from Double to BigDecimal
            BigDecimal price = BigDecimal.valueOf(priceDouble);

            // Create a new ReportSummaryDTO with the extracted data
            ReportSummaryDTO reportSummaryDTO = new ReportSummaryDTO(email, productName, productCount, price);

            // Add the DTO to the list
            reportSummaryDTOs.add(reportSummaryDTO);
        }

        return reportSummaryDTOs;
    }
}
