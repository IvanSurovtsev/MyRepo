package com.ivansurovtsev.demo.controllers;

import com.ivansurovtsev.demo.entities.CustomerRole;
import com.ivansurovtsev.demo.repositories.CustomerRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')") // Доступ только для администраторов
public class CustomerRoleController {
    private final CustomerRoleRepository roleRepository;

    // Получить все роли
    @GetMapping
    public List<CustomerRole> getAllRoles() {
        return roleRepository.findAll();
    }

    // Создать новую роль
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerRole createRole(@RequestBody CustomerRole role) {
        if (roleRepository.findByName(role.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Роль уже существует");
        }
        return roleRepository.save(role);
    }

    // Обновить роль
    @PutMapping("/{id}")
    public CustomerRole updateRole(@PathVariable UUID id, @RequestBody CustomerRole updatedRole) {
        CustomerRole role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Роль не найдена"));

        role.setName(updatedRole.getName());
        return roleRepository.save(role);
    }

    // Удалить роль
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Роль не найдена");
        }
        roleRepository.deleteById(id);
    }
}