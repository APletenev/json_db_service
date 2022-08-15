package com.example.json_db_service.service;

import com.example.json_db_service.model.entity.Customer;
import com.example.json_db_service.model.output.StatResult;
import com.example.json_db_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public List<Customer> findAllByLastName(String lastName) {
        return customerRepository.findAllByLastName(lastName);
    }

    @Transactional
    public List<Customer> customersPurchasedProductNotLessThan(String productName, int minTimes) {
        List<Customer> customers = customerRepository.findCustomersByPurchasesProductProductName(productName);
        customers.removeIf((Customer c) -> Collections.frequency(customers, c) < minTimes);
        List<Customer> distinctCustomers = customers.stream().distinct().collect(Collectors.toList());
        return distinctCustomers;
    }

    public List<Customer> customersWithTotalBetween(long minExpenses, long maxExpenses) {
        List<Customer> customers = customerRepository.findAll();
        customers.removeIf((Customer c) -> c.getSumOfPurchases() < minExpenses || c.getSumOfPurchases() > maxExpenses);
        return customers;
    }

    public List<Customer> badCustomers(int badCustomers) throws Exception {
        List<Customer> customers = customerRepository.findAll();
        if (badCustomers > customers.size()) {
            throw new Exception("Заданное число пассивных покупателей: " + badCustomers
                    + " больше чем общее число покупателей: " + customers.size());
        }
        customers.sort((c1, c2) -> (int) (c1.getCountOfPurchases() - c2.getCountOfPurchases()));
        return customers.subList(0, badCustomers);
    }

    public List<StatResult> statResults (Date startDate, Date endDate) {
        List<Customer> customers = customerRepository.findCustomersByPurchasesPurchaseDateBetween(startDate, endDate);
        List<StatResult> result = new ArrayList<>();
        for (Customer c :customers) {
            c.removePurchasesOutOfDates(startDate, endDate);
            result.add(new StatResult(c.getLastName()+" "+c.getFirstName(),null,c.getSumOfPurchases()));
        }
        return result;
    }

}
