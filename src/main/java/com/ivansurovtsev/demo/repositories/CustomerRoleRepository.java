package com.ivansurovtsev.demo.repositories;

import com.ivansurovtsev.demo.entities.CustomerRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRoleRepository extends JpaRepository<CustomerRole, UUID> {
    CustomerRole findByName(String name);
}
