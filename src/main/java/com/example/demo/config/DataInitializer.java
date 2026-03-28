package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize Admin User
        if (!userRepository.findByUsername("admin").isPresent()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setEmail("admin@flynow.com");
            admin.setFullName("Administrator");
            admin.setRole("ADMIN");
            admin.setActive(true);
            userRepository.save(admin);
            System.out.println("Admin user created: admin / admin123");
        }

        // Initialize Regular User
        if (!userRepository.findByUsername("ravi@1234").isPresent()) {
            User user = new User();
            user.setUsername("ravi@1234");
            user.setPassword("123456789");
            user.setEmail("ravi@flynow.com");
            user.setFullName("Ravi");
            user.setRole("USER");
            user.setActive(true);
            userRepository.save(user);
            System.out.println("User created: ravi@1234 / 123456789");
        }
    }
}

