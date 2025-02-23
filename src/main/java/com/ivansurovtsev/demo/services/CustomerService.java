package com.ivansurovtsev.demo.services;

import com.ivansurovtsev.demo.entities.Customer;
import com.ivansurovtsev.demo.repositories.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Ищем пользователя в базе данных
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));

        // Преобразуем Customer в UserDetails
        return User.builder()
                .username(customer.getUsername())
                .password(customer.getPassword())
                .roles(customer.getRoles().stream()
                        .map(role -> role.getName().replace("ROLE_", "")) // Убираем префикс ROLE_
                        .toArray(String[]::new))
                .build();
    }

    // Ваши другие методы (например, регистрация пользователя)
    public void registerCustomer(String username, String password, String roleName) {
        // Ваш код регистрации
    }
}