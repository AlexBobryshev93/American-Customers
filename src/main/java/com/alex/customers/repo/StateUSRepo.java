package com.alex.customers.repo;

import com.alex.customers.model.StateUS;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateUSRepo extends CrudRepository<StateUS, Integer> {
    StateUS findByName(String name);
}
