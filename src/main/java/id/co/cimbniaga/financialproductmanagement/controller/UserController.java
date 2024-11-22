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

    private final UserService userService;

    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userService.validateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        userService.LogLoginUser(user);
        return ResponseEntity.ok(token);
    }

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

}
