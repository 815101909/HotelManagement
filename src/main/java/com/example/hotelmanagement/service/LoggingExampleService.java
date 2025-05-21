package com.example.hotelmanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingExampleService {

    // 创建SLF4J Logger对象
    private static final Logger logger = LoggerFactory.getLogger(LoggingExampleService.class);
    
    public void performOperationWithLogging(String operation) {
        // 不同级别的日志
        logger.trace("TRACE级别: 正在执行操作: {}", operation); // 最详细，通常用于追踪详细执行流程
        logger.debug("DEBUG级别: 开始执行操作: {}", operation); // 调试信息，开发环境有用
        logger.info("INFO级别: 执行操作: {}", operation);      // 普通信息，默认级别
        
        try {
            // 模拟操作执行
            if ("error".equalsIgnoreCase(operation)) {
                throw new RuntimeException("操作执行失败");
            }
            
            // 记录成功信息
            logger.info("操作 '{}' 执行成功", operation);
        } catch (Exception e) {
            // 记录错误信息
            logger.warn("WARN级别: 操作执行出现警告");                // 警告信息
            logger.error("ERROR级别: 操作 '{}' 执行失败: {}", operation, e.getMessage()); // 错误信息
            logger.error("异常详情:", e);                       // 记录完整异常堆栈
        }
    }
    
    public void demonstrateParameterizedLogging(String username, int userId) {
        // SLF4J参数化日志输出 - 比字符串拼接更高效
        logger.info("用户 {} (ID: {}) 登录系统", username, userId);
        
        // 条件日志 - 避免不必要的字符串拼接
        if (logger.isDebugEnabled()) {
            logger.debug("详细的用户信息: 用户名={}, ID={}, 时间戳={}", username, userId, System.currentTimeMillis());
        }
    }
    
    public void demonstratePerformanceLogging() {
        logger.info("开始执行性能敏感操作");
        long startTime = System.currentTimeMillis();
        
        // 模拟执行一些操作
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        logger.info("操作完成，耗时: {}毫秒", (endTime - startTime));
    }
} 