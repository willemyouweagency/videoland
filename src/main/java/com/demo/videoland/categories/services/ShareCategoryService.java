package com.demo.videoland.categories.services;

import com.demo.videoland.categories.types.CategoryType;
import com.demo.videoland.entities.CustomerEntity;
import com.demo.videoland.entities.SubscribedCategoriesEntity;
import com.demo.videoland.repositories.CustomerRepository;
import com.demo.videoland.repositories.SubscribedCategoriesRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ShareCategoryService {

    CustomerRepository customerRepository;

    CategoryHelper categoryHelper;

    SubscribedCategoriesRepository subscribedCategoriesRepository;

    public ShareCategoryService(CustomerRepository customerRepository, CategoryHelper categoryHelper, SubscribedCategoriesRepository subscribedCategoriesRepository) {
        this.customerRepository = customerRepository;
        this.categoryHelper = categoryHelper;
        this.subscribedCategoriesRepository = subscribedCategoriesRepository;
    }

    // Multiple writes, transactional annotation needed to prevent DB inconsistencies
    @Transactional
    public void updateCustomerCategoryRelations(CustomerEntity customer1, CustomerEntity customer2, CategoryType categoryType) {
        SubscribedCategoriesEntity customer2SubscribedCategory = getCategoriesEntity(customer2, categoryType);
        customer1.addCategorySubscription(customer2SubscribedCategory);

        SubscribedCategoriesEntity customer1SubscribeCategory = getCategoriesEntity(customer1, categoryType);
        subscribedCategoriesRepository.delete(customer1SubscribeCategory);
        customer1.getCategorySubscriptions().remove(customer1SubscribeCategory);

        customerRepository.save(customer1);
    }

    public boolean subscriptionIsAlreadyShared(CustomerEntity customer, CategoryType categoryType) {
        return getCategoriesEntity(customer, categoryType) .getCustomer().size() > 1;
    }

    private SubscribedCategoriesEntity getCategoriesEntity(CustomerEntity customer, CategoryType categoryType) {
        return customer.getCategorySubscriptions().stream().filter(cat -> cat.getCategory() == categoryType).toList().get(0);
    }

    public boolean areBothCustomersSubscribed(CustomerEntity customer1, CustomerEntity customer2, CategoryType categoryType) {
        return categoryHelper.isSubscribed(customer1, categoryType) && categoryHelper.isSubscribed(customer2, categoryType);
    }

}
