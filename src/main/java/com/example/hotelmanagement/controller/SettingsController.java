package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.*;
import com.example.hotelmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {
    @Autowired
    private GeneralSettingsService generalSettingsService;
    @Autowired
    private NotificationSettingsService notificationSettingsService;
    @Autowired
    private BackupSettingsService backupSettingsService;

    // 基本设置
    @GetMapping("/general")
    public GeneralSettings getGeneralSettings() {
        return generalSettingsService.getSettings();
    }

    @PostMapping("/general")
    public boolean updateGeneralSettings(@RequestBody GeneralSettings settings) {
        return generalSettingsService.updateSettings(settings);
    }

    // 通知设置
    @GetMapping("/notification")
    public NotificationSettings getNotificationSettings() {
        return notificationSettingsService.getSettings();
    }

    @PostMapping("/notification")
    public boolean updateNotificationSettings(@RequestBody NotificationSettings settings) {
        return notificationSettingsService.updateSettings(settings);
    }

    // 备份设置
    @GetMapping("/backup")
    public BackupSettings getBackupSettings() {
        return backupSettingsService.getSettings();
    }

    @PostMapping("/backup")
    public boolean updateBackupSettings(@RequestBody BackupSettings settings) {
        return backupSettingsService.updateSettings(settings);
    }
} 