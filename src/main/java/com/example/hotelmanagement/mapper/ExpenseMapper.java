package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.Expense;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 支出数据访问层
 */
@Mapper
@Repository
public interface ExpenseMapper {
    
    /**
     * 添加支出记录
     * @param expense 支出对象
     * @return 影响行数
     */
    @Insert("INSERT INTO expense(category, amount, description, expense_date, create_time, update_time, remark) " +
            "VALUES(#{category}, #{amount}, #{description}, #{expenseDate}, #{createTime}, #{updateTime}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Expense expense);
    
    /**
     * 根据ID删除支出记录
     * @param id 支出ID
     * @return 影响行数
     */
    @Delete("DELETE FROM expense WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 更新支出记录
     * @param expense 支出对象
     * @return 影响行数
     */
    @Update("UPDATE expense SET category = #{category}, amount = #{amount}, description = #{description}, " +
            "expense_date = #{expenseDate}, update_time = #{updateTime}, remark = #{remark} WHERE id = #{id}")
    int update(Expense expense);
    
    /**
     * 根据ID查询支出记录
     * @param id 支出ID
     * @return 支出记录
     */
    @Select("SELECT * FROM expense WHERE id = #{id}")
    Expense findById(Long id);
    
    /**
     * 查询所有支出记录
     * @return 支出记录列表
     */
    @Select("SELECT * FROM expense ORDER BY expense_date DESC")
    List<Expense> findAll();
    
    /**
     * 根据日期范围查询支出记录
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 支出记录列表
     */
    @Select("SELECT * FROM expense WHERE expense_date >= #{startDate} AND expense_date < #{endDate} ORDER BY expense_date DESC")
    List<Expense> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    /**
     * 根据类别查询支出记录
     * @param category 支出类别
     * @return 支出记录列表
     */
    @Select("SELECT * FROM expense WHERE category = #{category} ORDER BY expense_date DESC")
    List<Expense> findByCategory(String category);
    
    /**
     * 统计各类别支出总额
     * @return 类别支出统计
     */
    @Select("SELECT category, SUM(amount) as total FROM expense GROUP BY category")
    List<Map<String, Object>> statisticsByCategory();
} 