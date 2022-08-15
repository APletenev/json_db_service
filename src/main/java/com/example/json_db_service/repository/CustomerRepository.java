package com.example.json_db_service.repository;

import com.example.json_db_service.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByLastName(String lastName);

    List<Customer> findCustomersByPurchasesProductProductName
            (String productName);

    List<Customer> findCustomersByPurchasesPurchaseDateBetween(Date startDate, Date endDate);


}