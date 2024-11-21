package id.co.cimbniaga.financialproductmanagement.controller;

import id.co.cimbniaga.financialproductmanagement.dto.UserRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.User;
import id.co.cimbniaga.financialproductmanagement.service.UserService;
import id.co.cimbniaga.financialproductmanagement.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private UserService userService;

    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    //User Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userService.validateUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(token);
    }

    //User Register
    //User update Stock

    @PostMapping("/register")
    public ResponseEntity<?> registerUser (@RequestBody UserRequestDTO userRequestDTO) {
        try {
            User user = new User();
            userRequestDTO.setRole("USER");
            user = userService.registerUser(userRequestDTO);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email already registered");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully register User");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserRequestDTO userRequestDTO) {
        User user = new User();
        userRequestDTO.setRole("ADMIN");
        user = userService.registerUser(userRequestDTO);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email already registered");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully register Admin");
    }


//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser (
//            @RequestBody UserRequestDTO userRequestDTO
//    ) {
//        try {
//            boolean isLogin = userService.LoginUser(userRequestDTO);
//            if(isLogin) {
//                return new ResponseEntity<>("Login Berhasil", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Login Gagal", HttpStatus.UNAUTHORIZED);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
