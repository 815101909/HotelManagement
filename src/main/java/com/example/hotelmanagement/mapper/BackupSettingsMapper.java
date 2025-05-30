package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.BackupSettings;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BackupSettingsMapper {
    @Select("SELECT * FROM backup_settings LIMIT 1")
    BackupSettings getSettings();

    @Update("UPDATE backup_settings SET auto_backup=#{autoBackup}, frequency=#{frequency}, retention=#{retention}, last_backup_time=#{lastBackupTime}, status=#{status}, updated_at=NOW() WHERE id=#{id}")
    int updateSettings(BackupSettings settings);

    @Insert("INSERT INTO backup_settings (auto_backup, frequency, retention, last_backup_time, status) VALUES (#{autoBackup}, #{frequency}, #{retention}, #{lastBackupTime}, #{status})")
    int insertSettings(BackupSettings settings);
} 