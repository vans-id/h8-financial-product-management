package id.co.cimbniaga.financialproductmanagement.controller;

import id.co.cimbniaga.financialproductmanagement.dto.UserRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.User;
import id.co.cimbniaga.financialproductmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //User Login
    //User Register
    //User update Stock

    @PostMapping("/register")
    public ResponseEntity<?> registerUser (
            @RequestBody UserRequestDTO userRequestDTO) {
        try {
            User user = userService.registerUser(userRequestDTO);
            return new ResponseEntity<>("Register Berhasil", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser (
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        try {
            boolean isLogin = userService.LoginUser(userRequestDTO);
            if(isLogin) {
                return new ResponseEntity<>("Login Berhasil", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Login Gagal", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
