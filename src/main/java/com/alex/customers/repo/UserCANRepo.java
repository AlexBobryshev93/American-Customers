package com.alex.customers.repo;

import com.alex.customers.model.User;
import com.alex.customers.model.UserCAN;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCANRepo extends CrudRepository<UserCAN, Long> {
    User findByUsername(String username);
}
