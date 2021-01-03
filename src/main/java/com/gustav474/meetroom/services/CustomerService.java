package com.gustav474.meetroom.services;

import com.gustav474.meetroom.DTO.CustomerDTO;
import com.gustav474.meetroom.entities.Customer;

/**
 * @author Sergey Lapshin
 */
public interface CustomerService {
    /**
     * Save customer to database
     * @param customerDTO
     * @return Customer entity
     */
    Customer saveCustomer(CustomerDTO customerDTO);

    /**
     * Find customer by login
     * @param login
     * @return Customer entity
     */
    Customer findByLogin(String login);

    /**
     * Find customer by id
     * @param id
     * @return Customer entity
     */
    Customer findById(Long id);
}
