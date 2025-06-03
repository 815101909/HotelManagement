package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Admin;

public interface AdminService {
    Admin findByUsername(String username);
    Admin findByEmail(String email);
    boolean register(Admin admin);
} 