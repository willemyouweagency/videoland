package com.demo.videoland.categories.controllers;

import com.demo.videoland.categories.types.CategoryType;
import com.demo.videoland.shared.StatusMessage;
import com.demo.videoland.categories.services.CategoryHelper;
import com.demo.videoland.entities.CustomerEntity;
import com.demo.videoland.entities.SubscribedCategoriesEntity;
import com.demo.videoland.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
public class SubscribeToCategory {

    CustomerRepository customerRepository;

    CategoryHelper categoryHelper;

    public SubscribeToCategory(CustomerRepository customerRepository, CategoryHelper categoryHelper) {
        this.customerRepository = customerRepository;
        this.categoryHelper = categoryHelper;
    }

    @PostMapping("/subscribe_category")
    public StatusMessage subscribeToCategory(@RequestBody CustomerRequestData customerRequestData) {

        CustomerEntity customer = customerRepository.findByEmail(customerRequestData.email);
        CategoryType categoryType = customerRequestData.availableCategory;

        if (customer == null) {
            return new StatusMessage("Subscription unsuccessful", "The customer does not exist");
        }

        if (categoryHelper.isSubscribed(customer, categoryType)) {
            return new StatusMessage("Subscription failed", "You are already subscribed to: " + categoryType);
        }

        saveCategoryToCustomer(categoryType, customer);

        return new StatusMessage("Subscription successful", "You are now subscribed to: " + categoryType);
    }

    private void saveCategoryToCustomer(CategoryType categoryType, CustomerEntity customer) {
        String dateString = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH).format(LocalDateTime.now());
        customer.addCategorySubscription(new SubscribedCategoriesEntity(dateString, 20, categoryType));

        customerRepository.save(customer);
    }

    record CustomerRequestData(String email, CategoryType availableCategory) {
    }
}
