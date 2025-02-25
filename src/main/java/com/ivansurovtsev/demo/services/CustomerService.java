package com.ivansurovtsev.demo.services;

import com.ivansurovtsev.demo.entities.Customer;
import com.ivansurovtsev.demo.entities.CustomerRole;
import com.ivansurovtsev.demo.repositories.CustomerRepository;
import com.ivansurovtsev.demo.repositories.CustomerRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRoleRepository customerRoleRepository;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
                           CustomerRoleRepository customerRoleRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRoleRepository = customerRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Ищем пользователя в базе данных
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
        return customer;
    }

    // Ваши другие методы (например, регистрация пользователя)
    public Customer registerCustomer(String username, String password) {
        Optional<Customer> ifPresent = customerRepository.findByUsername(username);
        if (ifPresent.isPresent()) {
            throw new RuntimeException("Юзернейм занят");
        }
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(passwordEncoder.encode(password));
        CustomerRole role = customerRoleRepository.findByName("STUDENT");
        customer.setRoles(List.of(role));
        return customerRepository.save(customer);
    }
}