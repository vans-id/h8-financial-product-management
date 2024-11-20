package id.co.cimbniaga.financialproductmanagement.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserRequestDTO {
    private Long id;
    private String email;
    private String password;
    private String role;
}

