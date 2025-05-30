package com.example.hotelmanagement.entity;

import java.util.Date;

public class NotificationSettings {
    private Integer id;
    private Boolean emailEnabled;
    private String emailServer;
    private Integer emailPort;
    private String emailUsername;
    private String emailPassword;
    private String emailFrom;
    private Boolean smsEnabled;
    private String smsProvider;
    private String smsApiKey;
    private String smsApiSecret;
    private Boolean eventBooking;
    private Boolean eventCheckin;
    private Boolean eventCheckout;
    private Boolean eventPayment;
    private Date updatedAt;
    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Boolean getEmailEnabled() { return emailEnabled; }
    public void setEmailEnabled(Boolean emailEnabled) { this.emailEnabled = emailEnabled; }
    public String getEmailServer() { return emailServer; }
    public void setEmailServer(String emailServer) { this.emailServer = emailServer; }
    public Integer getEmailPort() { return emailPort; }
    public void setEmailPort(Integer emailPort) { this.emailPort = emailPort; }
    public String getEmailUsername() { return emailUsername; }
    public void setEmailUsername(String emailUsername) { this.emailUsername = emailUsername; }
    public String getEmailPassword() { return emailPassword; }
    public void setEmailPassword(String emailPassword) { this.emailPassword = emailPassword; }
    public String getEmailFrom() { return emailFrom; }
    public void setEmailFrom(String emailFrom) { this.emailFrom = emailFrom; }
    public Boolean getSmsEnabled() { return smsEnabled; }
    public void setSmsEnabled(Boolean smsEnabled) { this.smsEnabled = smsEnabled; }
    public String getSmsProvider() { return smsProvider; }
    public void setSmsProvider(String smsProvider) { this.smsProvider = smsProvider; }
    public String getSmsApiKey() { return smsApiKey; }
    public void setSmsApiKey(String smsApiKey) { this.smsApiKey = smsApiKey; }
    public String getSmsApiSecret() { return smsApiSecret; }
    public void setSmsApiSecret(String smsApiSecret) { this.smsApiSecret = smsApiSecret; }
    public Boolean getEventBooking() { return eventBooking; }
    public void setEventBooking(Boolean eventBooking) { this.eventBooking = eventBooking; }
    public Boolean getEventCheckin() { return eventCheckin; }
    public void setEventCheckin(Boolean eventCheckin) { this.eventCheckin = eventCheckin; }
    public Boolean getEventCheckout() { return eventCheckout; }
    public void setEventCheckout(Boolean eventCheckout) { this.eventCheckout = eventCheckout; }
    public Boolean getEventPayment() { return eventPayment; }
    public void setEventPayment(Boolean eventPayment) { this.eventPayment = eventPayment; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
} 