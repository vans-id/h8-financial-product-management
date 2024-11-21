package id.co.cimbniaga.financialproductmanagement.repository;

import id.co.cimbniaga.financialproductmanagement.model.Report;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query(value =
            """
                SELECT u.email,p.name,COUNT(r.product_id),p.price FROM reports r
                INNER JOIN  products p
                ON r.product_id  = p.id
                INNER JOIN  users u
                ON r.user_id = u.id
                GROUP BY r.product_id,u.email,p.name
                ORDER BY  COUNT(r.product_id) DESC
            """, nativeQuery = true)
    List<Object[]> getTopProducts();
}