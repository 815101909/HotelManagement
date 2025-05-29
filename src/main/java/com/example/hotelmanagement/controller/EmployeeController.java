package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Employee;
import com.example.hotelmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.findById(id);
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @PostMapping("/add")
    public int addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }
    @PutMapping("/update")
    public int updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }
    @DeleteMapping("/delete/{id}")
    public int deleteEmployee(@PathVariable Integer id) {
        return employeeService.deleteEmployee(id);
    }
} 