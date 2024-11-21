package id.co.cimbniaga.financialproductmanagement.repository;

import id.co.cimbniaga.financialproductmanagement.model.Report;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {
    @Query("SELECT p.name, COUNT(r.product) FROM reports r " +
            "JOIN r.product p " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(r.product) DESC")
    List<Object[]> findTopProducts(PageRequest pageable);

}
