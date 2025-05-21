package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Employee;
import com.example.hotelmanagement.mapper.EmployeeMapper;
import com.example.hotelmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee findById(Integer id) {
        return employeeMapper.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeMapper.findAll();
    }
} 