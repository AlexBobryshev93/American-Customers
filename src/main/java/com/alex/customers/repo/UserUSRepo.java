package com.alex.customers.repo;

import com.alex.customers.model.User;
import com.alex.customers.model.UserUS;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUSRepo extends CrudRepository<UserUS, Long> {
    User findByUsername(String username);
}
