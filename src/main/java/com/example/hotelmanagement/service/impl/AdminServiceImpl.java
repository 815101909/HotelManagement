package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Admin;
import com.example.hotelmanagement.mapper.AdminMapper;
import com.example.hotelmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findByUsername(String username) {
        return adminMapper.findByUsername(username);
    }
} 