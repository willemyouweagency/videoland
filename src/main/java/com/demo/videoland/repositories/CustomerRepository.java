package com.demo.videoland.repositories;

import com.demo.videoland.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findByEmailAndPasswordHash(String email, String passwordHash);

    CustomerEntity findByEmail(String email);
}