package id.co.cimbniaga.financialproductmanagement.controller;

import id.co.cimbniaga.financialproductmanagement.model.User;
import id.co.cimbniaga.financialproductmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //User Login
    //User Register
    //User update Stock

    public ResponseEntity<?> findById


}
