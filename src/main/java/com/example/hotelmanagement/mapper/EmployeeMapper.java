package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("SELECT * FROM employee WHERE employee_id = #{id}")
    Employee findById(Integer id);

    @Select("SELECT * FROM employee")
    List<Employee> findAll();
}
