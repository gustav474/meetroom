package com.gustav474.meetroom.repositories;

import com.gustav474.meetroom.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByLogin(String login);
}
