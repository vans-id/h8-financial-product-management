package id.co.cimbniaga.financialproductmanagement.controller;

import id.co.cimbniaga.financialproductmanagement.dto.ReportSummaryDTO;
import id.co.cimbniaga.financialproductmanagement.dto.SimplifiedReportDTO;
import id.co.cimbniaga.financialproductmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public List<SimplifiedReportDTO> getTopProducts(@RequestBody ReportSummaryDTO reportSummaryDTO) {
        return reportService.getTopProducts(reportSummaryDTO);
    }
}
