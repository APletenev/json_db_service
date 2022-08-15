package com.example.json_db_service.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchase")
@NoArgsConstructor
@Getter
@Setter
public class Purchase {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column (name = "date")
    private Date purchaseDate;
}
