package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.dto.UserRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.User;
import id.co.cimbniaga.financialproductmanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //Register User
    public User registerUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole(userRequestDTO.getRole());

        return userRepository.save(user);
    }

    //Login User
    public boolean LoginUser(UserRequestDTO userRequestDTO){
        User user = userRepository.findByEmail(userRequestDTO.getEmail());
        return user!= null && passwordEncoder.matches(userRequestDTO.getPassword(), user.getPassword());
    }
}
