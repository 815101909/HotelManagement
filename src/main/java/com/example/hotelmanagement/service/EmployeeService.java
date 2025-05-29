package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Employee;
import java.util.List;

public interface EmployeeService {
    Employee findById(Integer id);
    List<Employee> findAll();

    int addEmployee(Employee employee);

    int updateEmployee(Employee employee);

    int deleteEmployee(Integer id);
} 