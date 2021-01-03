package com.gustav474.meetroom.services;

import com.gustav474.meetroom.DTO.CustomerDTO;
import com.gustav474.meetroom.entities.Customer;
import com.gustav474.meetroom.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Sergey Lapshin
 */
@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(CustomerDTO customerDTO) {
        Customer customer = convertCustomerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public Customer findByLogin(String login) {
        Customer customer = customerRepository.findByLogin(login);
        return customer;
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new NoSuchElementException("Can't find customer with id = " + id);
        }
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDto) {
        Customer customer = new Customer();
        customer.setLogin(customerDto.getLogin());
        String encodedPass = new BCryptPasswordEncoder().encode(customerDto.getPassword());
        customer.setPassword(encodedPass);
        return customer;
    }
}
