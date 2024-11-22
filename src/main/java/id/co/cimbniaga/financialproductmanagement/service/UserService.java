package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.dto.UserRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Messages;
import id.co.cimbniaga.financialproductmanagement.model.Report;
import id.co.cimbniaga.financialproductmanagement.model.User;
import id.co.cimbniaga.financialproductmanagement.repository.MessageRepository;
import id.co.cimbniaga.financialproductmanagement.repository.ReportRepository;
import id.co.cimbniaga.financialproductmanagement.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ReportRepository reportRepository;
    private final MessageRepository messageRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ReportRepository reportRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.reportRepository = reportRepository;
        this.messageRepository = messageRepository;
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

    public void LogLoginUser(User user){
        Messages messages = messageRepository.findByActivityType("LOGIN");
        if (messages == null)
        {
            messages = new Messages();
            messages.setActivityType("LOGIN");
            messages.setDetail("User logged in");
            messageRepository.save(messages);
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime uniqueTime = currentTime.plusNanos((long) (Math.random() * 1000));
        Report report = new Report();
        report.setUser(user);
        report.setTimestamp(Timestamp.valueOf(uniqueTime));
        report.setMessages(messages);
        reportRepository.save(report);
    }

    public User registerUser(UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            return null;
        }

        String encodedPassword = passwordEncoder.encode(userRequestDTO.getPassword());
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(userRequestDTO.getRole());

        return userRepository.save(user);
    }



}




