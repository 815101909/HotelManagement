package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.OperationLog;
import java.util.List;

public interface OperationLogService {
    int insert(OperationLog log);
    List<OperationLog> findRecent();
    List<OperationLog> findByAdminId(Integer adminId);
} 