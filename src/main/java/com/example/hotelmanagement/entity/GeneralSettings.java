package com.example.hotelmanagement.entity;

import java.util.Date;

public class GeneralSettings {
    private Integer id;
    private String hotelName;
    private String hotelAddress;
    private String hotelPhone;
    private String hotelEmail;
    private String hotelWebsite;
    private String currency;
    private String timezone;
    private String language;
    private String theme;
    private Date updatedAt;
    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public String getHotelAddress() { return hotelAddress; }
    public void setHotelAddress(String hotelAddress) { this.hotelAddress = hotelAddress; }
    public String getHotelPhone() { return hotelPhone; }
    public void setHotelPhone(String hotelPhone) { this.hotelPhone = hotelPhone; }
    public String getHotelEmail() { return hotelEmail; }
    public void setHotelEmail(String hotelEmail) { this.hotelEmail = hotelEmail; }
    public String getHotelWebsite() { return hotelWebsite; }
    public void setHotelWebsite(String hotelWebsite) { this.hotelWebsite = hotelWebsite; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
} 