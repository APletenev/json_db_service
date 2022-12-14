package com.example.json_db_service.service;

import com.example.json_db_service.model.entity.Customer;
import com.example.json_db_service.model.entity.Purchase;
import com.example.json_db_service.model.output.StatProductExpenses;
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

    public static boolean between(long i, long minValueInclusive, long maxValueInclusive) {
        return (i >= minValueInclusive && i <= maxValueInclusive);
    }
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
        return customerRepository.sumOfPurchases(customer) ;
    }

    public List<Customer> customersWithTotalBetween(long minExpenses, long maxExpenses) {
        List<Customer> customers = customerRepository.findAll();
        customers.removeIf((Customer c) -> ! between (SumOfPurchases(c), minExpenses, maxExpenses));
        return customers;
    }

    public List<Customer> badCustomers(int badCustomers) throws Exception {
        List<Customer> customers = customerRepository.findAll();
        if (badCustomers > customers.size()) {
            throw new Exception("???????????????? ?????????? ?????????????????? ??????????????????????: " + badCustomers
                    + " ???????????? ?????? ?????????? ?????????? ??????????????????????: " + customers.size());
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
            statProductExpenses = customerRepository.GetStatProductExpenses(c, startDate, endDate);
            long totalExpenses = statProductExpenses.stream().mapToLong(StatProductExpenses::getExpenses).reduce(Long::sum).orElse(0);
            result.add(new StatResult(c.getLastName() + " " + c.getFirstName(), statProductExpenses, totalExpenses));
        }
        result.sort((r1, r2) -> (int) (r2.getTotalExpenses() - r1.getTotalExpenses()));
        return result;
    }

}
