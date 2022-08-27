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

    public List<Customer> findAllByLastName(String lastName) {
        return customerRepository.findAllByLastName(lastName);
    }


    public List<Customer> customersPurchasedProductNotLessThan(String productName, int minTimes) {
        List<Customer> customers = customerRepository.findCustomersByPurchasesProductProductName(productName);
        customers.removeIf((Customer c) -> Collections.frequency(customers, c) < minTimes);
        List<Customer> distinctCustomers = customers.stream().distinct().collect(Collectors.toList());
        return distinctCustomers;
    }

    long SumOfPurchases(Customer customer) {
        return Math.round(customerRepository.sumOfPurchases(customer) / 100F); // Переводим из копеек в рубли
    }

    public List<Customer> customersWithTotalBetween(long minExpenses, long maxExpenses) {
        List<Customer> customers = customerRepository.findAll();
        customers.removeIf((Customer c) -> SumOfPurchases(c) < minExpenses || SumOfPurchases(c) > maxExpenses);
        return customers;
    }

    public List<Customer> badCustomers(int badCustomers) throws Exception {
        List<Customer> customers = customerRepository.findAll();
        if (badCustomers > customers.size()) {
            throw new Exception("Заданное число пассивных покупателей: " + badCustomers
                    + " больше чем общее число покупателей: " + customers.size());
        }
        customers.sort((c1, c2) -> (int) (customerRepository.countPurchasesByCustomer(c1) - customerRepository.countPurchasesByCustomer(c2)));
        return customers.subList(0, badCustomers);
    }

    @Transactional
    public void removePurchasesOutOfDates(List<Purchase> purchases, Date startDate, Date endDate) {
        purchases.removeIf((Purchase p) -> p.getPurchaseDate().before(startDate) || p.getPurchaseDate().after(endDate));
    }

    @Transactional
    public List<StatResult> statResults(Date startDate, Date endDate) {
        List<Customer> customers = customerRepository.findDistinctCustomersByPurchasesPurchaseDateBetween(startDate, endDate);
        List<StatProductExpenses> statProductExpenses = null;
        List<StatResult> result = new ArrayList<>();

        for (Customer c : customers) {
            removePurchasesOutOfDates(c.getPurchases(), startDate, endDate);
            Map<Product, List<Purchase>> purchasesByProduct = c.getPurchases().stream()
                    .collect(groupingBy(Purchase::getProduct));
            statProductExpenses = new ArrayList<>();

            long totalExpenses=0;
            for (Product p : purchasesByProduct.keySet()) {
                long expenses=(long) purchasesByProduct.get(p).size() * Math.round((p.getPrice() / 100F));
                statProductExpenses.add(new StatProductExpenses(p.getProductName(),expenses));
                totalExpenses+=expenses;
            }

            result.add(new StatResult(c.getLastName() + " " + c.getFirstName(), statProductExpenses, totalExpenses));
        }
        result.sort((r1, r2) -> (int) (r2.getTotalExpenses() - r1.getTotalExpenses()));
        return result;
    }

}
