package com.demo.videoland.crons;

import com.demo.videoland.categories.AvailableCategoriesData;
import com.demo.videoland.entities.CustomerEntity;
import com.demo.videoland.entities.SubscribedCategoriesEntity;
import com.demo.videoland.repositories.SubscribedCategoriesRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableScheduling
public class PaymentCron {

    static SubscribedCategoriesRepository subscribedCategoriesRepository;

    public PaymentCron(SubscribedCategoriesRepository subscribedCategoriesRepository) {
        this.subscribedCategoriesRepository = subscribedCategoriesRepository;
    }

    @Scheduled(cron = "0 15 10 25 * ?") // should be used in PROD
//    @Scheduled(fixedDelay = 2000) // For demo purposes
    public void scheduleSubscriptionPayments() {
        long totalSubscriptions = subscribedCategoriesRepository.count();

        int subscriptionsProcessed = 0;
        int pageSize = 1000;
        int currentPage = 0;

        while (totalSubscriptions > subscriptionsProcessed) {
            Pageable page = PageRequest.of(currentPage, pageSize);
            List<SubscribedCategoriesEntity> subscribedCategories = subscribedCategoriesRepository.findAll(page).toList();

            subscribedCategories.forEach(this::paySubscription);

            subscriptionsProcessed += pageSize;
            currentPage++;
        }
    }

    private void paySubscription(SubscribedCategoriesEntity subscribedCategoriesEntity) {
        // First month is free
        if (isFirstMonth(subscribedCategoriesEntity.getStartDate())) {
            return;
        }

        double price = AvailableCategoriesData.getPrice(subscribedCategoriesEntity.getCategory());

        // We need to retrieve the associated customers as well, these are normally lazily loaded but the method below will load them
        SubscribedCategoriesEntity subscribedCategory = subscribedCategoriesRepository.retrieveSubscriptionWithCustomers(subscribedCategoriesEntity.getId());

        List<CustomerEntity> customers = subscribedCategory.getCustomer();

        // If account is shared both customers pay halve
        if (customers.size() > 1) {
            price /= 2;
        };

        String message = " paid: " + price;
        customers.forEach(customer -> System.out.println(customer.getEmail() + message));
    }

    private boolean isFirstMonth(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd-MM-yyyy", Locale.ENGLISH );
        LocalDate subscriptionDate = LocalDate.parse(dateString, formatter );

        return LocalDate.now().minusMonths(1).compareTo(subscriptionDate) < 0;
    }

}



