package com.example.json_db_service.model.entity;

import com.example.json_db_service.model.Json;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Purchase> purchases;


    public Customer(List<Purchase> purchases) {
        this.purchases = purchases;

    }

}
