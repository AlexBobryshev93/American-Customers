package com.alex.customers.repo;

import com.alex.customers.model.UserUS;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUSRepo extends CrudRepository<UserUS, Long> {
    UserUS findByUsername(String username);
}
