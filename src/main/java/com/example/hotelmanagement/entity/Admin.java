package com.example.hotelmanagement.entity;

public class Admin {
    private Integer adminId;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private java.util.Date lastLogin;

    // getter和setter
    public Integer getAdminId() { return adminId; }
    public void setAdminId(Integer adminId) { this.adminId = adminId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public java.util.Date getLastLogin() { return lastLogin; }
    public void setLastLogin(java.util.Date lastLogin) { this.lastLogin = lastLogin; }
} 