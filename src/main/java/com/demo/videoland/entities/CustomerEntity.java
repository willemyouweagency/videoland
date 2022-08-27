package com.demo.videoland.entities;

import com.demo.videoland.security.BcryptHashing;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "customers")
public class CustomerEntity {

    public CustomerEntity() {
    }

    public CustomerEntity(String email, String password, List<SubscribedCategoriesEntity> categorySubscriptions) {
        this.email = email;
        this.passwordHash = BcryptHashing.hashBcrypt(password);
        this.categorySubscriptions = categorySubscriptions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    private List<SubscribedCategoriesEntity> categorySubscriptions = new ArrayList<>();

    private String email;

    private String passwordHash;

    public List<SubscribedCategoriesEntity> getCategorySubscriptions() {
        return categorySubscriptions;
    }

    public void setCategorySubscriptions(List<SubscribedCategoriesEntity> categorySubscriptions) {
        this.categorySubscriptions = categorySubscriptions;
    }

    public void addCategorySubscription(SubscribedCategoriesEntity subscribedCategoriesEntity) {
        categorySubscriptions.add(subscribedCategoriesEntity);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}