package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Expense;
import com.example.hotelmanagement.mapper.ExpenseMapper;
import com.example.hotelmanagement.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 支出服务实现类
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {
    
    @Autowired
    private ExpenseMapper expenseMapper;
    
    @Override
    @Transactional
    public Long addExpense(Expense expense) {
        // 设置创建时间和更新时间
        Date now = new Date();
        expense.setCreateTime(now);
        expense.setUpdateTime(now);
        
        // 如果没有设置支出日期，则使用当前日期
        if (expense.getExpenseDate() == null) {
            expense.setExpenseDate(now);
        }
        
        expenseMapper.insert(expense);
        return expense.getId();
    }
    
    @Override
    @Transactional
    public boolean deleteExpense(Long id) {
        return expenseMapper.deleteById(id) > 0;
    }
    
    @Override
    @Transactional
    public boolean updateExpense(Expense expense) {
        // 设置更新时间
        expense.setUpdateTime(new Date());
        return expenseMapper.update(expense) > 0;
    }
    
    @Override
    public Expense getExpenseById(Long id) {
        return expenseMapper.findById(id);
    }
    
    @Override
    public List<Expense> getAllExpenses() {
        return expenseMapper.findAll();
    }
    
    @Override
    public List<Expense> getExpensesByDateRange(Date startDate, Date endDate) {
        System.out.println("[ExpenseServiceImpl] getExpensesByDateRange 收到参数: startDate=" + startDate + ", endDate=" + endDate);
        if (startDate != null && endDate != null) {
            // JDK8写法，补全endDate为次日0点
            java.time.LocalDate end = endDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            java.time.LocalDateTime endDateTime = end.plusDays(1).atStartOfDay();
            Date realEndDate = Date.from(endDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
            System.out.println("[ExpenseServiceImpl] getExpensesByDateRange 补全后: startDate=" + startDate + ", endDate=" + realEndDate);
            return expenseMapper.findByDateRange(startDate, realEndDate);
        }
        return new java.util.ArrayList<>();
    }
    
    @Override
    public List<Expense> getExpensesByCategory(String category) {
        return expenseMapper.findByCategory(category);
    }
    
    @Override
    public List<Map<String, Object>> getExpenseStatistics() {
        return expenseMapper.statisticsByCategory();
    }
} 