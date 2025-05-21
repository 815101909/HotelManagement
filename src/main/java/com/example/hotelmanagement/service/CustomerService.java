package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Customer;
import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<Customer> findAll();
    Customer findById(Integer id);
    int insert(Customer customer);
    int update(Customer customer);
    int delete(Integer id);
    
    // 新增统计方法
    Map<String, Object> getCustomerStats();
    int getTotalCount();
    int getNewMonthlyCount();
    int getVipCount();
    int getActiveCount();
    double getAverageVisits();
    // 可根据需要补充新增、修改、删除等方法
} 