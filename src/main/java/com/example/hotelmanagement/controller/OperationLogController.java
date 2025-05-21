package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.OperationLog;
import com.example.hotelmanagement.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/operation-log")
public class OperationLogController {
    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/recent")
    public List<OperationLog> getRecentLogs() {
        return operationLogService.findRecent();
    }

    @GetMapping("/admin/{adminId}")
    public List<OperationLog> getLogsByAdmin(@PathVariable Integer adminId) {
        return operationLogService.findByAdminId(adminId);
    }

    @PostMapping
    public int addLog(@RequestBody OperationLog log) {
        return operationLogService.insert(log);
    }
} 