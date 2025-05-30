package com.example.hotelmanagement.entity;

import java.util.Date;

public class BackupSettings {
    private Integer id;
    private Boolean autoBackup;
    private String frequency;
    private Integer retention;
    private Date lastBackupTime;
    private String status;
    private Date updatedAt;
    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Boolean getAutoBackup() { return autoBackup; }
    public void setAutoBackup(Boolean autoBackup) { this.autoBackup = autoBackup; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public Integer getRetention() { return retention; }
    public void setRetention(Integer retention) { this.retention = retention; }
    public Date getLastBackupTime() { return lastBackupTime; }
    public void setLastBackupTime(Date lastBackupTime) { this.lastBackupTime = lastBackupTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
} 