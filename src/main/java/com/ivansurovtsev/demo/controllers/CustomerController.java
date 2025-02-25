package com.ivansurovtsev.demo.controllers;

import com.ivansurovtsev.demo.entities.Customer;
import com.ivansurovtsev.demo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/customers")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')") // Доступ только для администраторов
public class CustomerController {
    private final CustomerRepository customerRepository;

    // Получить всех пользователей
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Получить пользователя по ID
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
    }

    // Обновить данные пользователя
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable UUID id, @RequestBody Customer updatedCustomer) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        customer.setUsername(updatedCustomer.getUsername());
        // Пароль обновляется отдельно (через сервис с хэшированием)
        return customerRepository.save(customer);
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        customerRepository.deleteById(id);
    }
}