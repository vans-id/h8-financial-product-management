package id.co.cimbniaga.financialproductmanagement.controller;

import id.co.cimbniaga.financialproductmanagement.dto.ReportSummaryDTO;
import id.co.cimbniaga.financialproductmanagement.dto.SimplifiedReportDTO;
import id.co.cimbniaga.financialproductmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public List<SimplifiedReportDTO> getTopProducts(@RequestBody ReportSummaryDTO reportSummaryDTO) {
        return reportService.getTopProducts(reportSummaryDTO);
    }
}
