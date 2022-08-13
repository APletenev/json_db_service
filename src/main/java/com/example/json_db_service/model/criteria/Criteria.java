package com.example.json_db_service.model.criteria;

import com.example.json_db_service.model.entity.Customer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Criteria extends LinkedHashMap<String, Object> {

    public List<Customer> getResults() {
        ArrayList<Customer> results = new ArrayList<>();
        Customer test =  new Customer();
        test.setFirstName("Иван"); test.setLastName("Иванов");
        results.add(test);
        return results;
    }
}
