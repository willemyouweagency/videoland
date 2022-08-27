package com.demo.videoland.categories.services;

import com.demo.videoland.categories.types.CategoryType;
import com.demo.videoland.entities.CustomerEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryHelper {
    public boolean isSubscribed(CustomerEntity customer, CategoryType categoryType) {
        return customer.getCategorySubscriptions().stream().anyMatch(cat -> cat.getCategory() == categoryType);
    }
}
