package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.model.Product;
import id.co.cimbniaga.financialproductmanagement.model.Report;
import id.co.cimbniaga.financialproductmanagement.model.User;
import id.co.cimbniaga.financialproductmanagement.repository.ReportRepository;
import id.co.cimbniaga.financialproductmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Pr

    public List<Report> getTopProducts() {
        // Get the results from the repository (List of Object[] as the result)
        List<Object[]> results = reportRepository.getTopProducts();

        // List to hold the processed report data
        List<Report> reports = new ArrayList<>();

        // Process the results
        for (Object[] result : results) {
            // Extract data from the result array
            String email = (String) result[0];  // Email of the user
            String productName = (String) result[1];  // Product name
            Long productCount = (Long) result[2];  // Count of products
            Double priceDouble = (Double) result[3];  // Price of the product

            // Convert the price from Double to BigDecimal
            BigDecimal price = BigDecimal.valueOf(priceDouble);

            // Retrieve the User and Product entities based on email and product name
            User user = userRepository.findByEmail(email);  // Assuming you have a method findByEmail

            // Create a new Report object and populate it with the values
            Report report = new Report();
            report.setActivityType("Top Product Report");

            // Set the User and Product entities
            report.setUser(user);  // Set the User entity

            // Set other fields

            // Add the report to the list
            reports.add(report);
        }

        return reports;
    }

        return reports;
    }
}