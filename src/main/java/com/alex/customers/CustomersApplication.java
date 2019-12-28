package com.alex.customers;

import com.alex.customers.model.StateUS;
import com.alex.customers.model.User;
import com.alex.customers.model.UserCAN;
import com.alex.customers.model.UserUS;
import com.alex.customers.repo.StateUSRepo;
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
    public CommandLineRunner dataLoader(UserUSRepo userUSRepo, UserCANRepo userCANRepo, StateUSRepo stateUSRepo) {
        return args -> {
            if (((List) stateUSRepo.findAll()).isEmpty()
                && ((List) userUSRepo.findAll()).isEmpty()
                && ((List) userCANRepo.findAll()).isEmpty()
            ) {
                StateUS state1 = new StateUS("Alabama");
                StateUS state2 = new StateUS("Georgia");
                StateUS state3 = new StateUS("Mississippi");
                StateUS state4 = new StateUS("Florida");

                state1.addNeighbor(state2);
                state1.addNeighbor(state3);
                state1.addNeighbor(state4);
                state2.addNeighbor(state4);

                UserUS user1 = new UserUS();
                user1.setUsername("user1");
                user1.setPassword(encoder.encode("pass"));
                user1.setState(state1);

                UserCAN user2 = new UserCAN();
                user2.setUsername("user2");
                user2.setPassword(encoder.encode("pass"));
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
