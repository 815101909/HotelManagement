package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface AdminMapper {
    @Select("SELECT * FROM admin WHERE username = #{username}")
    Admin findByUsername(String username);

    @Select("SELECT * FROM admin WHERE email = #{email}")
    Admin findByEmail(String email);

    @Insert("INSERT INTO admin (username, password, name, phone, email, create_time) VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAdmin(Admin admin);
} 