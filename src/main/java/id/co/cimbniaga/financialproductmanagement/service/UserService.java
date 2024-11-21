package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.model.User;
import id.co.cimbniaga.financialproductmanagement.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User validateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return null;
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return null;
        }

        return user.get();
    }

    public User registerUser(UserRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail).isPresent()) {
            return null;
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setEmail(userRequestDTO.getEmail);
        user.setPassword(userRequestDTO.getPassword);
        user.setRole(userRequestDTO.getRole);

        return userRepository.save(user);



        //Login User
        public boolean LoginUser(UserRequestDTO userRequestDTO){
            User user = userRepository.findByEmail(userRequestDTO.getEmail());
            return user!= null;
        }
    }




