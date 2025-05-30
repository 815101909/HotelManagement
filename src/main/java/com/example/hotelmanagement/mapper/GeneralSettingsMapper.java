package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.GeneralSettings;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GeneralSettingsMapper {
    @Select("SELECT * FROM general_settings LIMIT 1")
    GeneralSettings getSettings();

    @Update("UPDATE general_settings SET hotel_name=#{hotelName}, hotel_address=#{hotelAddress}, hotel_phone=#{hotelPhone}, hotel_email=#{hotelEmail}, hotel_website=#{hotelWebsite}, currency=#{currency}, timezone=#{timezone}, language=#{language}, theme=#{theme}, updated_at=NOW() WHERE id=#{id}")
    int updateSettings(GeneralSettings settings);

    @Insert("INSERT INTO general_settings (hotel_name, hotel_address, hotel_phone, hotel_email, hotel_website, currency, timezone, language, theme) VALUES (#{hotelName}, #{hotelAddress}, #{hotelPhone}, #{hotelEmail}, #{hotelWebsite}, #{currency}, #{timezone}, #{language}, #{theme})")
    int insertSettings(GeneralSettings settings);
} 