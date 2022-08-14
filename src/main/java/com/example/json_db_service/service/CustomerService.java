package com.example.json_db_service.service;

import com.example.json_db_service.model.entity.Customer;
import com.example.json_db_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public List<Customer> findAllByLastName(String lastName) {
        return customerRepository.findAllByLastName(lastName);
    }

    public List<Customer> CustomersPurchasedProductNotLessThan(String productName, int minTimes) {
        List<Customer> customers = customerRepository.findCustomersByPurchasesProductProductName(productName);
        customers.removeIf((Customer c) -> Collections.frequency(customers, c)<minTimes);
        List<Customer> distinctCustomers = customers.stream().distinct().collect(Collectors.toList());
        return distinctCustomers;
    }

}
