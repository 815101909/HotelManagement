package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.BackupSettings;

public interface BackupSettingsService {
    BackupSettings getSettings();
    boolean updateSettings(BackupSettings settings);
} 