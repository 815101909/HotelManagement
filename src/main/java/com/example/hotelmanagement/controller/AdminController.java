package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Admin;
import com.example.hotelmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public Admin getAdminByUsername(@RequestParam String username) {
        return adminService.findByUsername(username);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        Admin admin = adminService.findByUsername(username);
        if (admin != null) {
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (admin.getPassword().equals(md5Password)) {
                admin.setPassword(null); // 不返回密码
                return ResponseEntity.ok(admin);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("message", "用户名或密码错误"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Admin admin) {
        if (admin.getUsername() == null || admin.getPassword() == null || admin.getEmail() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "参数不完整"));
        }
        boolean success = adminService.register(admin);
        if (success) {
            return ResponseEntity.ok(Collections.singletonMap("message", "注册成功"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("message", "用户名或邮箱已存在"));
        }
    }
} 