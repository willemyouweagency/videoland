package com.demo.videoland.categories.controllers;

import com.demo.videoland.categories.types.CategoryType;
import com.demo.videoland.categories.services.ShareCategoryService;
import com.demo.videoland.shared.StatusMessage;
import com.demo.videoland.entities.CustomerEntity;
import com.demo.videoland.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShareCategory {

    CustomerRepository customerRepository;

    ShareCategoryService shareCategoryService;


    public ShareCategory(CustomerRepository customerRepository, ShareCategoryService shareCategoryService) {
        this.customerRepository = customerRepository;
        this.shareCategoryService = shareCategoryService;
    }

    @PostMapping("/share_category")
    public StatusMessage subscribeToCategory(@RequestBody CustomerRequestData customerRequestData) {

        CustomerEntity customer1 = customerRepository.findByEmail(customerRequestData.email);
        CustomerEntity customer2 = customerRepository.findByEmail(customerRequestData.customer);

        if (customer2 == null || customer1 == null) {
            return new StatusMessage("Subscription sharing unsuccessful", "One of the customers does not exists");
        }

        CategoryType categoryType = customerRequestData.availableCategory;

        if (!shareCategoryService.areBothCustomersSubscribed(customer1, customer2, categoryType)) {
            return new StatusMessage("Subscription sharing unsuccessful", "Both customers need to be subscribed in order to share a subscription");
        }

        if (shareCategoryService.subscriptionIsAlreadyShared(customer2, categoryType)) {
            return new StatusMessage("Subscription sharing unsuccessful", "The customer is already sharing a subscription on: " + categoryType);
        }

        shareCategoryService.updateCustomerCategoryRelations(customer1, customer2, categoryType);

        return new StatusMessage("Subscription successful", "You are now sharing a subscription with: " + customerRequestData.customer);
    }

    record CustomerRequestData(String email, String customer, CategoryType availableCategory) {
    }
}
