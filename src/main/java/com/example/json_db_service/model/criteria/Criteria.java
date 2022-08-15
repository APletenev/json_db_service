package com.example.json_db_service.model.criteria;

import com.example.json_db_service.ApplicationContextHolder;
import com.example.json_db_service.model.entity.Customer;
import com.example.json_db_service.service.CustomerService;

import java.util.LinkedHashMap;
import java.util.List;

public class Criteria extends LinkedHashMap<String, Object> {

    public List<Customer> getResults() throws Exception {
        List<Customer> result = null;
        String keyset = keySet().toString();
        CustomerService customerService = ApplicationContextHolder.getContext().getBean(CustomerService.class);
        switch (keyset) {
            case "[lastName]":
                return customerService.findAllByLastName((String) get("lastName"));
            case "[productName, minTimes]":
                return customerService.CustomersPurchasedProductNotLessThan((String) get("productName"), (int) get("minTimes"));
            case "[minExpenses, maxExpenses]":
                return customerService.CustomersWithTotalBetween(
                        ((Number) get("minExpenses")).longValue() * 100L,
                        ((Number) get("maxExpenses")).longValue() * 100L);
            case "[badCustomers]":
                return customerService.BadCustomers((int)get("badCustomers"));
            default:
                throw new Exception("Неправильные параметры запроса: " + keyset);
        }

    }
}
