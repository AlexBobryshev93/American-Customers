package com.alex.customers;

import com.alex.customers.model.User;
import com.alex.customers.model.UserCAN;
import com.alex.customers.model.UserUS;
import com.alex.customers.repo.UserCANRepo;
import com.alex.customers.repo.UserUSRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class CustomersApplication {
    @Autowired
    PasswordEncoder encoder;

    @Bean
    public CommandLineRunner dataLoader(UserUSRepo userUSRepo, UserCANRepo userCANRepo) {
        return args -> {
            if (((List) userUSRepo.findAll()).isEmpty() && ((List) userCANRepo.findAll()).isEmpty()) {
                UserUS user1 = new UserUS();
                user1.setUsername("user1");
                user1.setPassword(encoder.encode("pass"));
                user1.setCountry(User.Country.USA);
                user1.setState("Alabama");

                UserCAN user2 = new UserCAN();
                user2.setUsername("user2");
                user2.setPassword(encoder.encode("pass"));
                user2.setCountry(User.Country.CANADA);
                user2.setProvince(UserCAN.Province.ON);
                user2.setCity("Toronto");

                userUSRepo.save(user1);
                userCANRepo.save(user2);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }

}
