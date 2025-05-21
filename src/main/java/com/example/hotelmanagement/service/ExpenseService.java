package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Expense;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 支出服务接口
 */
public interface ExpenseService {
    
    /**
     * 添加支出记录
     * @param expense 支出对象
     * @return 支出ID
     */
    Long addExpense(Expense expense);
    
    /**
     * 删除支出记录
     * @param id 支出ID
     * @return 是否成功
     */
    boolean deleteExpense(Long id);
    
    /**
     * 更新支出记录
     * @param expense 支出对象
     * @return 是否成功
     */
    boolean updateExpense(Expense expense);
    
    /**
     * 根据ID查询支出记录
     * @param id 支出ID
     * @return 支出记录
     */
    Expense getExpenseById(Long id);
    
    /**
     * 查询所有支出记录
     * @return 支出记录列表
     */
    List<Expense> getAllExpenses();
    
    /**
     * 根据日期范围查询支出记录
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 支出记录列表
     */
    List<Expense> getExpensesByDateRange(Date startDate, Date endDate);
    
    /**
     * 根据类别查询支出记录
     * @param category 支出类别
     * @return 支出记录列表
     */
    List<Expense> getExpensesByCategory(String category);
    
    /**
     * 统计各类别支出总额
     * @return 类别支出统计
     */
    List<Map<String, Object>> getExpenseStatistics();
} 