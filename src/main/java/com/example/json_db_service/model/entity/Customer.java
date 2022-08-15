package com.example.json_db_service.model.entity;

import com.example.json_db_service.model.Json;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "customer")
@NoArgsConstructor
@Getter
@Setter
public class Customer implements Json {
    @Id
    @JsonIgnore
    private long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Purchase> purchases;

    public Customer(List<Purchase> purchases) {
        this.purchases = purchases;

    }

    @JsonIgnore
    @Transactional
    public long getSumOfPurchases() {
        long sum = 0;
        for (Purchase p : purchases) {
            sum += p.getProduct().getPrice();
        }
        return sum;
    }

    @JsonIgnore
    @Transactional
    public long getCountOfPurchases() {
        return purchases.size();
    }


    @JsonIgnore
    @Transactional
    public void removePurchasesOutOfDates(Date startDate, Date endDate) {
        purchases.removeIf((Purchase p) -> p.getPurchaseDate().before(startDate) || p.getPurchaseDate().after(endDate));
    }

}
