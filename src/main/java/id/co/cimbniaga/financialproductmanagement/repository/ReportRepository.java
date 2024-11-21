package id.co.cimbniaga.financialproductmanagement.repository;

import id.co.cimbniaga.financialproductmanagement.model.Report;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query(value =
            """
                SELECT  p.name, COUNT(r.product_id), p.price , p.stock
                FROM reports r
                INNER JOIN products p ON r.product_id = p.id
                INNER JOIN users u ON r.user_id = u.id
                WHERE r.timestamp BETWEEN :startDate AND :endDate
                GROUP BY r.product_id, u.email, p.name
                ORDER BY COUNT(r.product_id) DESC
                LIMIT 3
            """, nativeQuery = true)
    List<Object[]> getTopProducts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}