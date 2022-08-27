package com.demo.videoland.entities;

import com.demo.videoland.categories.types.CategoryType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subscribed_categories")
public class SubscribedCategoriesEntity {
    public SubscribedCategoriesEntity() {
    }

    public SubscribedCategoriesEntity(String startDate, int remainingContent, CategoryType categoryType) {
        StartDate = startDate;
        this.remainingContent = remainingContent;
        this.categoryType = categoryType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(mappedBy = "categorySubscriptions")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    private List<CustomerEntity> customer;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    public List<CustomerEntity> getCustomer() {
        return customer;
    }

    public void setCustomer(List<CustomerEntity> customer) {
        this.customer = customer;
    }

    public CategoryType getCategory() {
        return categoryType;
    }

    public void setCategory(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    private String StartDate;

    private int remainingContent;

    public int getRemainingContent() {
        return remainingContent;
    }

    public void setRemainingContent(int remainingContent) {
        this.remainingContent = remainingContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }
}