package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Customer;
import com.example.hotelmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Integer id) {
        return customerService.findById(id);
    }

    @PostMapping
    public int addCustomer(@RequestBody Customer customer) {
        return customerService.insert(customer);
    }

    @PutMapping
    public int updateCustomer(@RequestBody Customer customer) {
        return customerService.update(customer);
    }

    @DeleteMapping("/{id}")
    public int deleteCustomer(@PathVariable Integer id) {
        return customerService.delete(id);
    }
    
    @GetMapping("/stats")
    public Map<String, Object> getCustomerStats() {
        return customerService.getCustomerStats();
    }
    
    @GetMapping("/stats/total")
    public int getTotalCount() {
        return customerService.getTotalCount();
    }
    
    @GetMapping("/stats/monthly-new")
    public int getNewMonthlyCount() {
        return customerService.getNewMonthlyCount();
    }
    
    @GetMapping("/stats/vip")
    public int getVipCount() {
        return customerService.getVipCount();
    }
    
    @GetMapping("/stats/active")
    public int getActiveCount() {
        return customerService.getActiveCount();
    }
    
    @GetMapping("/stats/average-visits")
    public double getAverageVisits() {
        return customerService.getAverageVisits();
    }
} 