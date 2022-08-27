package com.demo.videoland.login.controllers;

import com.demo.videoland.shared.StatusMessage;
import com.demo.videoland.entities.CustomerEntity;
import com.demo.videoland.repositories.CustomerRepository;
import com.demo.videoland.security.BcryptHashing;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerLogin {
    public CustomerRepository customerRepository;

    public CustomerLogin(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/customer_login")
    public StatusMessage login(@RequestBody CustomerLoginCredentials customerLoginCredentials) {
        String passwordHash = BcryptHashing.hashBcrypt(customerLoginCredentials.password);

        CustomerEntity customer = customerRepository.findByEmailAndPasswordHash(customerLoginCredentials.email, passwordHash);

        if (customer != null) {
            return new StatusMessage("Login successful", "Login successful");
        }

        return new StatusMessage("Login failed", "Invalid username or password");
    }

    record CustomerLoginCredentials(String email, String password){};
}
