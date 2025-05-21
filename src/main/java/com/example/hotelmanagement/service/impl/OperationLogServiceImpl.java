package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.OperationLog;
import com.example.hotelmanagement.mapper.OperationLogMapper;
import com.example.hotelmanagement.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public int insert(OperationLog log) {
        return operationLogMapper.insert(log);
    }

    @Override
    public List<OperationLog> findRecent() {
        return operationLogMapper.findRecent();
    }

    @Override
    public List<OperationLog> findByAdminId(Integer adminId) {
        return operationLogMapper.findByAdminId(adminId);
    }
} 