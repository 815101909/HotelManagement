package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.NotificationSettings;
import com.example.hotelmanagement.mapper.NotificationSettingsMapper;
import com.example.hotelmanagement.service.NotificationSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationSettingsServiceImpl implements NotificationSettingsService {
    @Autowired
    private NotificationSettingsMapper mapper;

    @Override
    public NotificationSettings getSettings() {
        return mapper.getSettings();
    }

    @Override
    public boolean updateSettings(NotificationSettings settings) {
        return mapper.updateSettings(settings) > 0;
    }
} 