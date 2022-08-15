package com.example.json_db_service.service;

import com.example.json_db_service.model.entity.Customer;
import com.example.json_db_service.model.entity.Product;
import com.example.json_db_service.model.entity.Purchase;
import com.example.json_db_service.model.output.StatProductExpenses;
import com.example.json_db_service.model.output.StatResult;
import com.example.json_db_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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

    public List<StatResult> statResults(Date startDate, Date endDate) {
        List<Customer> customers = customerRepository.findDistinctCustomersByPurchasesPurchaseDateBetween(startDate, endDate);
        List<StatProductExpenses> statProductExpenses = null;
        List<StatResult> result = new ArrayList<>();

        for (Customer c : customers) {
            c.removePurchasesOutOfDates(startDate, endDate);
            Map<Product, List<Purchase>> purchasesByProduct = c.getPurchases().stream()
                    .collect(groupingBy(Purchase::getProduct));
            statProductExpenses = new ArrayList<>();

            for (Product p : purchasesByProduct.keySet()) {
                statProductExpenses.add(new StatProductExpenses(p.getProductName(),
                        (long) purchasesByProduct.get(p).size() * Math.round((p.getPrice() / 100F))));
            }

            result.add(new StatResult(c.getLastName() + " " + c.getFirstName(), statProductExpenses, c.getSumOfPurchases()));
        }
        result.sort((r1, r2) -> (int) (r2.getTotalExpenses() - r1.getTotalExpenses()));
        return result;
    }

}
