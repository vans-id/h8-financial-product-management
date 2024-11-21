package id.co.cimbniaga.financialproductmanagement.repository;

import id.co.cimbniaga.financialproductmanagement.dto.UserRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
