package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.NotificationSettings;

public interface NotificationSettingsService {
    NotificationSettings getSettings();
    boolean updateSettings(NotificationSettings settings);
} 