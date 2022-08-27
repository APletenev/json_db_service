package com.example.json_db_service.repository;

import com.example.json_db_service.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByLastName(String lastName);

    List<Customer> findCustomersByPurchasesProductProductName
            (String productName);

    List<Customer> findDistinctCustomersByPurchasesPurchaseDateBetween(Date startDate, Date endDate);

    @Query("SELECT SUM(p.product.price) FROM Purchase p WHERE p.customer = ?1")
    long sumOfPurchases(Customer customer);

    @Query("select COUNT(p) from Purchase p where p.customer = ?1")
    long countPurchasesByCustomer (Customer customer);


}