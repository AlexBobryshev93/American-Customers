package com.alex.customers.repo;

import com.alex.customers.model.UserCAN;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCANRepo extends CrudRepository<UserCAN, Long> {
    UserCAN findByUsername(String username);
    int deleteByUsername(String username);
}
