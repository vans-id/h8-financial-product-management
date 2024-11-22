package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.dto.UserRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.User;
import id.co.cimbniaga.financialproductmanagement.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void validateUserTest() {
//        User user = new User();
//        user.setEmail("admin3@example.com");
//        user.setPassword("1234567890");
//
//        Mockito.when(userRepository.findByEmail("admin3@example.com")).thenReturn(Optional.of(user));
//
//        User user1 = userService.validateUser("admin3@example.com", "1234567890");
//
//        Assertions.assertNotNull(user1);
//        Assertions.assertEquals(user.getPassword(), "1234567890");
//
////        Mockito.verify(userRepository
//    }

//    @Test
//    void registerUserTest() {
//        UserRequestDTO userRequestDTO = new UserRequestDTO();
//        String password = passwordEncoder.encode("123456");
//        userRequestDTO.setEmail("user3@example.com");
//        userRequestDTO.setPassword(password);
//        userRequestDTO.setEmail("user3@example.com");
//
//        Mockito.when(userRepository.findByEmail("user3@example.com").isPresent()).thenReturn(null);
//
//        User user1 = userService.registerUser(userRequestDTO);
//
//        Assertions.assertNull(user1);
//    }
}
