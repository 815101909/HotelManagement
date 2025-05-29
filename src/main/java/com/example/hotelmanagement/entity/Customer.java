package com.example.hotelmanagement.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

public class Customer {
    private Integer id;
    @ExcelProperty("客户姓名")
    private String name;
    private String gender;
    @ExcelProperty("联系电话")
    private String phone;
    @ExcelProperty("身份证")
    private String idCard;
    @ExcelProperty("电子邮箱")
    private String email;
    @ExcelProperty("注册时间")
    private Date createTime;
    @ExcelProperty("客户类型")
    private String type;
    @ExcelProperty("客户状态")
    private String status;
    private String idType;
    @ExcelProperty("地址")
    private String address;
    private String notes;
    @ExcelProperty("消费金额")
    private Double spent; // 消费金额
    @ExcelProperty("入住次数")
    private Integer visits; // 入住次数

    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getIdCard() { return idCard; }
    public void setIdCard(String idCard) { this.idCard = idCard; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getIdType() { return idType; }
    public void setIdType(String idType) { this.idType = idType; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Double getSpent() { return spent; }
    public void setSpent(Double spent) { this.spent = spent; }
    public Integer getVisits() { return visits; }
    public void setVisits(Integer visits) { this.visits = visits; }
} 