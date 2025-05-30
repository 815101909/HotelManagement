package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.GeneralSettings;

public interface GeneralSettingsService {
    GeneralSettings getSettings();
    boolean updateSettings(GeneralSettings settings);
} 