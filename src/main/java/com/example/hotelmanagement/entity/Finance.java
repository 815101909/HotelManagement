package com.example.hotelmanagement.entity;

public class Finance {
    private Integer id;
    private String month; // 格式如 2024-06
    private Double revenue;
    private Double cost;
    private Double profit;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public Double getRevenue() { return revenue; }
    public void setRevenue(Double revenue) { this.revenue = revenue; }
    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }
    public Double getProfit() { return profit; }
    public void setProfit(Double profit) { this.profit = profit; }
} 