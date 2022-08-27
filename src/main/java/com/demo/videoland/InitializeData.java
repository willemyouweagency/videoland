package com.demo.videoland;

import com.demo.videoland.categories.types.CategoryType;
import com.demo.videoland.entities.CustomerEntity;
import com.demo.videoland.entities.SubscribedCategoriesEntity;
import com.demo.videoland.repositories.CustomerRepository;
import com.demo.videoland.repositories.SubscribedCategoriesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class InitializeData implements CommandLineRunner {

    CustomerRepository customerRepository;

    SubscribedCategoriesRepository subscribedCategoriesRepository;

    InitializeData(CustomerRepository customerRepository, SubscribedCategoriesRepository subscribedCategoriesRepository) {
        this.customerRepository = customerRepository;
        this.subscribedCategoriesRepository = subscribedCategoriesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String dateString = getDateString();

        List<SubscribedCategoriesEntity> subscribedCategories1 = Arrays.asList(
                new SubscribedCategoriesEntity(dateString, 10, CategoryType.INTERNATIONAL_FILMS),
                new SubscribedCategoriesEntity(dateString, 14, CategoryType.DUTCH_SERIES)
        );

        List<SubscribedCategoriesEntity> subscribedCategories2 = Arrays.asList(
                new SubscribedCategoriesEntity("25-08-2021", 11, CategoryType.INTERNATIONAL_FILMS),
                new SubscribedCategoriesEntity(dateString, 15, CategoryType.DUTCH_FILMS)
        );

//        List<SubscribedCategoriesEntity> subscribedCategories3 = Arrays.asList(
//                new SubscribedCategoriesEntity(dateString, 11, CategoryType.INTERNATIONAL_FILMS),
//                new SubscribedCategoriesEntity(dateString, 15, CategoryType.DUTCH_FILMS)
//        );

        customerRepository.save(new CustomerEntity("willem@gmail.com", "password", subscribedCategories1));
        customerRepository.save(new CustomerEntity("henk@gmail.com", "password123", subscribedCategories2));
//        customerRepository.save(new CustomerEntity("willem@gmail.comm", "password", subscribedCategories3));
    }

    private static String getDateString() {
        LocalDateTime ldt = LocalDateTime.now();
        return DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH).format(ldt);
    }
}
