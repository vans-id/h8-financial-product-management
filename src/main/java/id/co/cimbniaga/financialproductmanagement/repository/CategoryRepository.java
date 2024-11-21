package id.co.cimbniaga.financialproductmanagement.repository;

import id.co.cimbniaga.financialproductmanagement.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
