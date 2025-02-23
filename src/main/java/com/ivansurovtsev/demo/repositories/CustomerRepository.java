package com.ivansurovtsev.demo.repositories;

import com.ivansurovtsev.demo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByUsername(String username); // Возвращаем Optional<Customer>
}