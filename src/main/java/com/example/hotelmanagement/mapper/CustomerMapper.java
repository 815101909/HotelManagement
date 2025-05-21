package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Delete;
import java.util.List;

@Mapper
public interface CustomerMapper {
    // Methods defined in XML file
    List<Customer> findAll();
    Customer findById(Integer id);
    int insert(Customer customer);
    int update(Customer customer);
    int delete(Integer id);
} 