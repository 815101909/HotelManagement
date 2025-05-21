package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Expense;
import com.example.hotelmanagement.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支出控制器
 */
@RestController
@RequestMapping("/api/admin/finance")
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;
    
    /**
     * 添加支出记录
     * @param expense 支出对象
     * @return 响应结果
     */
    @PostMapping("/expenses")
    public ResponseEntity<Map<String, Object>> addExpense(@RequestBody Expense expense) {
        Long id = expenseService.addExpense(expense);
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("message", "支出记录添加成功");
        return ResponseEntity.ok(result);
    }
    
    /**
     * 删除支出记录
     * @param id 支出ID
     * @return 响应结果
     */
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Map<String, Object>> deleteExpense(@PathVariable Long id) {
        boolean success = expenseService.deleteExpense(id);
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("message", "支出记录删除成功");
            return ResponseEntity.ok(result);
        } else {
            result.put("message", "支出记录删除失败，可能记录不存在");
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 更新支出记录
     * @param id 支出ID
     * @param expense 支出对象
     * @return 响应结果
     */
    @PutMapping("/expenses/{id}")
    public ResponseEntity<Map<String, Object>> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        expense.setId(id);
        boolean success = expenseService.updateExpense(expense);
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("message", "支出记录更新成功");
            return ResponseEntity.ok(result);
        } else {
            result.put("message", "支出记录更新失败，可能记录不存在");
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 根据ID查询支出记录
     * @param id 支出ID
     * @return 支出记录
     */
    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        if (expense != null) {
            return ResponseEntity.ok(expense);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 查询所有支出记录
     * @return 支出记录列表
     */
    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }
    
    /**
     * 获取支出数据，包含分类统计
     * @return 支出统计和明细
     */
    @GetMapping("/expenses/summary")
    public ResponseEntity<Map<String, Object>> getExpensesSummary() {
        List<Expense> expenses = expenseService.getAllExpenses();
        Map<String, Object> result = new HashMap<>();
        
        // 计算总支出
        BigDecimal total = BigDecimal.ZERO;
        Map<String, BigDecimal> categories = new HashMap<>();
        
        for (Expense expense : expenses) {
            BigDecimal amount = expense.getAmount();
            if (amount == null) amount = BigDecimal.ZERO;
            
            // 总计
            total = total.add(amount);
            
            // 按类别统计
            String category = expense.getCategory();
            if (category == null) category = "其他";
            
            if (!categories.containsKey(category)) {
                categories.put(category, BigDecimal.ZERO);
            }
            categories.put(category, categories.get(category).add(amount));
        }
        
        result.put("total", total);
        result.put("categories", categories);
        result.put("items", expenses);
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * 根据日期范围查询支出记录
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 支出记录列表
     */
    @GetMapping("/expenses/date-range")
    public ResponseEntity<List<Expense>> getExpensesByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(expenseService.getExpensesByDateRange(startDate, endDate));
    }
    
    /**
     * 根据类别查询支出记录
     * @param category 支出类别
     * @return 支出记录列表
     */
    @GetMapping("/expenses/category/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(expenseService.getExpensesByCategory(category));
    }
    
    /**
     * 获取支出统计数据
     * @return 统计数据
     */
    @GetMapping("/expenses/statistics")
    public ResponseEntity<List<Map<String, Object>>> getExpenseStatistics() {
        return ResponseEntity.ok(expenseService.getExpenseStatistics());
    }
} 