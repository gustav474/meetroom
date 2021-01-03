package com.gustav474.meetroom.services;

import com.gustav474.meetroom.entities.Customer;
import com.gustav474.meetroom.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Lapshin
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByLogin(username);

        if (customer == null) {
            throw new UsernameNotFoundException(username);
        }

        UserDetails user = User.withUsername(customer.getLogin()).password(customer.getPassword()).authorities("USER").build();

        return user;
    }
}
