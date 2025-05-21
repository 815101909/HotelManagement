package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.service.LoggingExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logging")
public class LoggingDemoController {

    private static final Logger logger = LoggerFactory.getLogger(LoggingDemoController.class);
    
    @Autowired
    private LoggingExampleService loggingExampleService;
    
    @GetMapping("/demo")
    public String demoLogging(@RequestParam(defaultValue = "standard") String operation) {
        logger.info("收到日志演示请求，操作类型: {}", operation);
        
        loggingExampleService.performOperationWithLogging(operation);
        
        logger.info("日志演示完成");
        return "日志演示完成，请查看控制台和日志文件";
    }
    
    @GetMapping("/user/{userId}")
    public String userLogging(@PathVariable int userId, @RequestParam String username) {
        logger.info("收到用户日志演示请求");
        
        loggingExampleService.demonstrateParameterizedLogging(username, userId);
        
        return "用户日志演示完成";
    }
    
    @GetMapping("/performance")
    public String performanceLogging() {
        logger.info("收到性能日志演示请求");
        
        loggingExampleService.demonstratePerformanceLogging();
        
        return "性能日志演示完成";
    }
} 