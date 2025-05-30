package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.GeneralSettings;
import com.example.hotelmanagement.mapper.GeneralSettingsMapper;
import com.example.hotelmanagement.service.GeneralSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralSettingsServiceImpl implements GeneralSettingsService {
    @Autowired
    private GeneralSettingsMapper mapper;

    @Override
    public GeneralSettings getSettings() {
        return mapper.getSettings();
    }

    @Override
    public boolean updateSettings(GeneralSettings settings) {
        return mapper.updateSettings(settings) > 0;
    }
} 