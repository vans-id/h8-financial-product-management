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
        LocalDate startDate = reportSummaryDTO.getStart_date();
        LocalDate endDate = reportSummaryDTO.getEnd_date();

        List<Object[]> results = reportRepository.getTopProducts(startDate, endDate);

        List<SimplifiedReportDTO> simplifiedReportDTOS = new ArrayList<>();

        for (Object[] result : results) {
            String productName = (String) result[0];
            Long productCount = (Long) result[1];
            Double priceDouble = (Double) result[2];
            Long stock = (Long) result[3];

            BigDecimal price = BigDecimal.valueOf(priceDouble);

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
