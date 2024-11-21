package id.co.cimbniaga.financialproductmanagement.service;


import id.co.cimbniaga.financialproductmanagement.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {


    @Autowired
    private ReportRepository reportRepository;

    public void getTopProducts() {
        // Use JpaSort.unsafe to sort by the count of products
        PageRequest pageable = PageRequest.of(0, 3, JpaSort.unsafe(Sort.Order.desc("COUNT(r.product)").getDirection()));

        // Fetch the top 3 products ordered by count
        List<Object[]> results = reportRepository.findTopProducts(pageable);

        // Print the results
        results.forEach(row -> {
            String productName = (String) row[0];
            Long count = (Long) row[1];
            System.out.println("Product: " + productName + ", Count: " + count);
        });
    }
}
