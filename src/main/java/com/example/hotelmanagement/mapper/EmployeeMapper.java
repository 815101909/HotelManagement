package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("SELECT * FROM employee WHERE employee_id = #{id}")
    Employee findById(Integer id);

    @Select("SELECT * FROM employee")
    List<Employee> findAll();

    @Insert("INSERT INTO employee (name, gender, phone, position, hire_date, salary, status, create_time) " +
            "VALUES (#{name}, #{gender}, #{phone}, #{position}, #{hireDate}, #{salary}, #{status}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addEmployee(Employee employee);

    int updateEmployee(Employee employee);

    int deleteEmployee(Integer id);
}
