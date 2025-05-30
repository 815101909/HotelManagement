package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.NotificationSettings;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NotificationSettingsMapper {
    @Select("SELECT * FROM notification_settings LIMIT 1")
    NotificationSettings getSettings();

    @Update("UPDATE notification_settings SET email_enabled=#{emailEnabled}, email_server=#{emailServer}, email_port=#{emailPort}, email_username=#{emailUsername}, email_password=#{emailPassword}, email_from=#{emailFrom}, sms_enabled=#{smsEnabled}, sms_provider=#{smsProvider}, sms_api_key=#{smsApiKey}, sms_api_secret=#{smsApiSecret}, event_booking=#{eventBooking}, event_checkin=#{eventCheckin}, event_checkout=#{eventCheckout}, event_payment=#{eventPayment}, updated_at=NOW() WHERE id=#{id}")
    int updateSettings(NotificationSettings settings);

    @Insert("INSERT INTO notification_settings (email_enabled, email_server, email_port, email_username, email_password, email_from, sms_enabled, sms_provider, sms_api_key, sms_api_secret, event_booking, event_checkin, event_checkout, event_payment) VALUES (#{emailEnabled}, #{emailServer}, #{emailPort}, #{emailUsername}, #{emailPassword}, #{emailFrom}, #{smsEnabled}, #{smsProvider}, #{smsApiKey}, #{smsApiSecret}, #{eventBooking}, #{eventCheckin}, #{eventCheckout}, #{eventPayment})")
    int insertSettings(NotificationSettings settings);
} 