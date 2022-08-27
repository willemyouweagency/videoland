package com.demo.videoland.categories;

import com.demo.videoland.categories.services.CategoryHelper;
import com.demo.videoland.categories.types.CategoryType;
import com.demo.videoland.entities.CustomerEntity;
import com.demo.videoland.entities.SubscribedCategoriesEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CategoryHelperTest {

    CategoryHelper categoryHelper = new CategoryHelper();

    CustomerEntity customer = new CustomerEntity(
            "test@gmail.com",
            "hashString",
            Arrays.asList(
                    new SubscribedCategoriesEntity("", 0, CategoryType.INTERNATIONAL_FILMS)
            ));

    @BeforeEach
    void setUp() {

    }

    @Test
    void customerIsSubscribed() {
        assertTrue(categoryHelper.isSubscribed(customer, CategoryType.INTERNATIONAL_FILMS));
    }

    @Test
    void customerIsNotSubscribed() {
        assertFalse(categoryHelper.isSubscribed(customer, CategoryType.DUTCH_FILMS));
    }
}