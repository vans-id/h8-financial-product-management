package id.co.cimbniaga.financialproductmanagement.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String email;
    private String password;
    private String role;
}

