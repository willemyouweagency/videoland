package com.demo.videoland.repositories;

import com.demo.videoland.entities.SubscribedCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubscribedCategoriesRepository  extends JpaRepository<SubscribedCategoriesEntity, Long> {
    @Query("SELECT subscription FROM SubscribedCategoriesEntity subscription JOIN FETCH subscription.customer customer WHERE subscription.id=?1")
    SubscribedCategoriesEntity retrieveSubscriptionWithCustomers(long id);
}
