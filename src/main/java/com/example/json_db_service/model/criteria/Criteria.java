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
                result = customerService.findAllByLastName((String)get("lastName"));
                break;
            case "[productName, minTimes]":
                break;
            case "[minExpenses, maxExpenses]":
                break;
            case "[badCustomers]":
                break;
            default:
                throw new Exception("Неправильные параметры запроса: " + keyset);
        }

        return result;

    }
}
