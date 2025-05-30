package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.BackupSettings;
import com.example.hotelmanagement.mapper.BackupSettingsMapper;
import com.example.hotelmanagement.service.BackupSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackupSettingsServiceImpl implements BackupSettingsService {
    @Autowired
    private BackupSettingsMapper mapper;

    @Override
    public BackupSettings getSettings() {
        return mapper.getSettings();
    }

    @Override
    public boolean updateSettings(BackupSettings settings) {
        return mapper.updateSettings(settings) > 0;
    }
} 