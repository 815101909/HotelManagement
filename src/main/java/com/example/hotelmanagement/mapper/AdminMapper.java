package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("SELECT * FROM admin WHERE username = #{username}")
    Admin findByUsername(String username);
} 