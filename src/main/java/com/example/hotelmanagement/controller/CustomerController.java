package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Customer;
import com.example.hotelmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
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

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportCustomers() {
        try {
            // 1. 获取所有客户数据
            List<Customer> customers = customerService.findAll();

            // 2. 生成Excel文件（这里用EasyExcel或POI，示例用EasyExcel）
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            com.alibaba.excel.EasyExcel.write(out, Customer.class)
                    .sheet("客户数据")
                    .doWrite(customers);

            // 3. 设置响应头
            String fileName = URLEncoder.encode("客户数据.xlsx", "UTF-8").replaceAll("\\+", "%20");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + fileName);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
} 