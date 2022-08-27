package com.demo.videoland.categories.controllers;

import com.demo.videoland.categories.services.GetCategoriesService;
import com.demo.videoland.categories.types.CategoryResponse;
import com.demo.videoland.entities.CustomerEntity;
import com.demo.videoland.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
public class GetCategories {

    CustomerRepository customerRepository;

    GetCategoriesService getCategoriesService;

    public GetCategories(CustomerRepository customerRepository, GetCategoriesService getCategoriesService) {
        this.customerRepository = customerRepository;
        this.getCategoriesService = getCategoriesService;
    }

    @GetMapping("/categories")
    public CategoryResponse getCategories(@RequestBody CustomerData customerData) {
        CustomerEntity customer = customerRepository.findByEmail(customerData.email);

        if (customer == null) {
            throw new EntityNotFoundException("The customer is not found, please provide an email address of a valid customer");
        }

        return getCategoriesService.getCategoryResponse(customer);
    }

    record CustomerData(String email) {}
}
