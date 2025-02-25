package com.ivansurovtsev.demo.controllers;

import com.ivansurovtsev.demo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity <?> register(@RequestParam String username,
                                   @RequestParam String password) {
        return ResponseEntity.ok(customerService.registerCustomer(username, password));
    }
}
