package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.dto.ReportSummaryDTO;
import id.co.cimbniaga.financialproductmanagement.dto.SimplifiedReportDTO;
import id.co.cimbniaga.financialproductmanagement.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;

    @Test
    void getTopProducts() {
        ReportSummaryDTO reportSummaryDTO = new ReportSummaryDTO();
        reportSummaryDTO.setStartDate(LocalDate.of(2022, 12, 25));
        reportSummaryDTO.setEndDate(LocalDate.of(2022, 12, 26));

        // Mocking the repository response
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[]{"Product A", 10L, 1000.0, 10L});
        results.add(new Object[]{"Product B", 5L, 500.0, 5L});
        when(reportRepository.getTopProducts(any(), any())).thenReturn(results);

        // Execute the service method
        List<SimplifiedReportDTO> simplifiedReportDTOS = reportService.getTopProducts(reportSummaryDTO);

        // Verify the result
        assertNotNull(simplifiedReportDTOS);
        assertEquals(2, simplifiedReportDTOS.size());

        SimplifiedReportDTO productA = simplifiedReportDTOS.get(0);
        assertEquals("Product A", productA.getProductName());
        assertEquals(10L, productA.getProductCount());
        assertEquals(1000.0, productA.getPrice());
        assertEquals(10L, productA.getStock());

        SimplifiedReportDTO productB = simplifiedReportDTOS.get(1);
        assertEquals("Product B", productB.getProductName());
        assertEquals(5L, productB.getProductCount());
        assertEquals(500.0, productB.getPrice());
        assertEquals(5L, productB.getStock());

        // Negative test case
        when(reportRepository.getTopProducts(any(), any())).thenReturn(new ArrayList<>());
        List<SimplifiedReportDTO> emptyList = reportService.getTopProducts(new ReportSummaryDTO());
        assertNotNull(emptyList);
        assertEquals(0, emptyList.size());


        // Test case with end date before start date
        ReportSummaryDTO invalidDate = new ReportSummaryDTO();
        invalidDate.setStartDate(LocalDate.of(2022, 12, 26));
        invalidDate.setEndDate(LocalDate.of(2022, 12, 25));
        List<SimplifiedReportDTO> invalidDateList = reportService.getTopProducts(invalidDate);
        assertNotNull(invalidDateList);
        assertEquals(0, invalidDateList.size());

    }
}