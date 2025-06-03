package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Admin;
import com.example.hotelmanagement.mapper.AdminMapper;
import com.example.hotelmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findByUsername(String username) {
        return adminMapper.findByUsername(username);
    }

    @Override
    public Admin findByEmail(String email) {
        return adminMapper.findByEmail(email);
    }

    @Override
    public boolean register(Admin admin) {
        // 检查用户名或邮箱是否已存在
        if (adminMapper.findByUsername(admin.getUsername()) != null ||
            adminMapper.findByEmail(admin.getEmail()) != null) {
            return false;
        }
        // 密码加密
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        admin.setCreateTime(new java.util.Date());
        return adminMapper.insertAdmin(admin) > 0;
    }
} 