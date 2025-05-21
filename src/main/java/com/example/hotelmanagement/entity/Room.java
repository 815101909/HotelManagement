package com.example.hotelmanagement.entity;

import java.math.BigDecimal;

public class Room {
    private Integer id;
    private String roomNumber;
    private String type;
    private String status;
    private BigDecimal price;
    private Integer floor;
    private String description;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
} 