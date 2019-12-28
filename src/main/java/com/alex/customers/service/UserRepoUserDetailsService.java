package com.alex.customers.service;

import com.alex.customers.model.User;
import com.alex.customers.repo.UserCANRepo;
import com.alex.customers.repo.UserUSRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepoUserDetailsService implements UserDetailsService {
    private UserUSRepo userUSRepo;
    private UserCANRepo userCANRepo;

    //@Autowired
    public UserRepoUserDetailsService(UserUSRepo userUSRepo, UserCANRepo userCANRepo) {
        this.userUSRepo = userUSRepo;
        this.userCANRepo = userCANRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userUSRepo.findByUsername(username);
        if (user == null) user = userCANRepo.findByUsername(username);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities());
        }

        throw new UsernameNotFoundException(
                "User '" + username + "' not found");
    }
}

