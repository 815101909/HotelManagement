package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Customer;
import com.example.hotelmanagement.mapper.CustomerMapper;
import com.example.hotelmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Customer> findAll() {
        return customerMapper.findAll();
    }

    @Override
    public Customer findById(Integer id) {
        return customerMapper.findById(id);
    }

    @Override
    public int insert(Customer customer) {
        return customerMapper.insert(customer);
    }

    @Override
    public int update(Customer customer) {
        return customerMapper.update(customer);
    }

    @Override
    public int delete(Integer id) {
        return customerMapper.delete(id);
    }
    
    @Override
    public Map<String, Object> getCustomerStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCount", getTotalCount());
        stats.put("newMonthlyCount", getNewMonthlyCount());
        stats.put("vipCount", getVipCount());
        stats.put("activeCount", getActiveCount());
        stats.put("averageVisits", getAverageVisits());
        return stats;
    }
    
    @Override
    public int getTotalCount() {
        List<Customer> customers = customerMapper.findAll();
        return customers != null ? customers.size() : 0;
    }
    
    @Override
    public int getNewMonthlyCount() {
        List<Customer> allCustomers = customerMapper.findAll();
        if (allCustomers == null || allCustomers.isEmpty()) {
            return 0;
        }
        
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = LocalDate.of(now.getYear(), now.getMonth(), 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        return (int) allCustomers.stream()
            .filter(c -> {
                try {
                    // 获取创建时间，根据实际类型进行处理
                    String createTimeStr;
                    Object createTimeObj = c.getCreateTime();
                    
                    if (createTimeObj == null) {
                        return false;
                    } else if (createTimeObj instanceof String) {
                        createTimeStr = (String) createTimeObj;
                    } else if (createTimeObj instanceof java.util.Date) {
                        // 如果是Date类型，转换为字符串
                        java.util.Date date = (java.util.Date) createTimeObj;
                        createTimeStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
                    } else {
                        // 其他情况转为字符串
                        createTimeStr = createTimeObj.toString();
                    }
                    
                    if (createTimeStr.isEmpty()) {
                        return false;
                    }
                    
                    // 处理日期格式
                    if (createTimeStr.length() > 10) {
                        createTimeStr = createTimeStr.substring(0, 10);
                    }
                    LocalDate createDate = LocalDate.parse(createTimeStr, formatter);
                    return createDate.isAfter(firstDayOfMonth.minusDays(1));
                } catch (Exception e) {
                    return false;
                }
            })
            .count();
    }
    
    @Override
    public int getVipCount() {
        List<Customer> allCustomers = customerMapper.findAll();
        if (allCustomers == null || allCustomers.isEmpty()) {
            return 0;
        }
        
        return (int) allCustomers.stream()
            .filter(c -> "vip".equalsIgnoreCase(c.getType()))
            .count();
    }
    
    @Override
    public int getActiveCount() {
        List<Customer> allCustomers = customerMapper.findAll();
        if (allCustomers == null || allCustomers.isEmpty()) {
            return 0;
        }
        
        return (int) allCustomers.stream()
            .filter(c -> c.getVisits() != null && c.getVisits() > 0)
            .count();
    }
    
    @Override
    public double getAverageVisits() {
        List<Customer> allCustomers = customerMapper.findAll();
        if (allCustomers == null || allCustomers.isEmpty()) {
            return 0.0;
        }
        
        int totalVisits = allCustomers.stream()
            .mapToInt(c -> c.getVisits() != null ? c.getVisits() : 0)
            .sum();
            
        return (double) totalVisits / allCustomers.size();
    }
} 