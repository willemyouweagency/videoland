package com.demo.videoland.categories.services;

import com.demo.videoland.categories.AvailableCategoriesData;
import com.demo.videoland.categories.types.AvailableCategory;
import com.demo.videoland.categories.types.CategoryType;
import com.demo.videoland.categories.types.CategoryResponse;
import com.demo.videoland.categories.types.SubscribedCategoriesExtended;
import com.demo.videoland.entities.CustomerEntity;
import com.demo.videoland.entities.SubscribedCategoriesEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCategoriesService {
    private List<SubscribedCategoriesExtended> getCategoriesExtendedList(List<SubscribedCategoriesEntity> categorySubscriptions) {
        return categorySubscriptions.stream().map(category -> new SubscribedCategoriesExtended(
                AvailableCategoriesData.getName(category.getCategory()),
                category.getRemainingContent(),
                AvailableCategoriesData.getPrice(category.getCategory()),
                category.getStartDate()
        )).collect(Collectors.toList());
    }

    public CategoryResponse getCategoryResponse(
            CustomerEntity customer
    ) {
        List<SubscribedCategoriesEntity> categorySubscriptions = customer.getCategorySubscriptions();
        List<SubscribedCategoriesExtended> subscribedCategoriesExtendedList = getCategoriesExtendedList(categorySubscriptions);

        ArrayList<AvailableCategory> availableCategories = new ArrayList<>();
        for (CategoryType categoryType : CategoryType.values()) {
            if (categorySubscriptions.stream().anyMatch(cat -> cat.getCategory() == categoryType)) {
                continue;
            }

            availableCategories.add(AvailableCategoriesData.availableCategories.get(categoryType));
        }

        return new CategoryResponse(availableCategories, subscribedCategoriesExtendedList);
    }
}
