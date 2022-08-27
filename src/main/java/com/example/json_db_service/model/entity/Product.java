package com.example.json_db_service.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    private long id;

    @Column(name = "name")
    private String productName;

    @Column(name = "price")
    private long price;

    @OneToMany(mappedBy = "product")
    private List<Purchase> purchases;
}
