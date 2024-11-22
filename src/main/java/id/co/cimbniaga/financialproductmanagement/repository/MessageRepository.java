package id.co.cimbniaga.financialproductmanagement.repository;

import id.co.cimbniaga.financialproductmanagement.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MessageRepository extends JpaRepository<Messages,Long> {
    Messages findByActivityType(String activityType);
}
