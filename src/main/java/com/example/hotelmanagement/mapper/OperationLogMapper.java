package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.OperationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface OperationLogMapper {
    @Insert("INSERT INTO operation_log (admin_id, action, detail, create_time) VALUES (#{adminId}, #{action}, #{detail}, NOW())")
    int insert(OperationLog log);

    @Select("SELECT * FROM operation_log ORDER BY create_time DESC LIMIT 100")
    List<OperationLog> findRecent();

    @Select("SELECT * FROM operation_log WHERE admin_id = #{adminId} ORDER BY create_time DESC LIMIT 100")
    List<OperationLog> findByAdminId(Integer adminId);
} 