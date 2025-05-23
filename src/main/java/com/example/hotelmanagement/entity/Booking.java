package com.example.hotelmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.math.BigDecimal;

public class Booking {
    private Integer id;
    private Integer customerId;
    private Integer roomId;
    private Date checkinDate;
    private Date checkoutDate;
    private String status; // booked/checkedin/checkedout/cancelled
    private Double totalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private Date createdAt;
    private String paymentStatus;
    private Boolean syncedToFinance; // 是否已同步到财务系统

    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public Integer getRoomId() { return roomId; }
    public void setRoomId(Integer roomId) { this.roomId = roomId; }
    public Date getCheckinDate() { return checkinDate; }
    public void setCheckinDate(Date checkinDate) { this.checkinDate = checkinDate; }
    public Date getCheckoutDate() { return checkoutDate; }
    public void setCheckoutDate(Date checkoutDate) { this.checkoutDate = checkoutDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public Boolean getSyncedToFinance() { return syncedToFinance; }
    public void setSyncedToFinance(Boolean syncedToFinance) { this.syncedToFinance = syncedToFinance; }
    
    // 添加兼容方法，为了支持FinanceController
    public BigDecimal getTotalAmount() {
        if (totalPrice == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(totalPrice);
    }
    
    public Date getCreateTime() {
        return createdAt;
    }
} 