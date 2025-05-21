package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.Finance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import java.util.List;

@Mapper
public interface FinanceMapper {
    @Select("SELECT * FROM finance")
    List<Finance> findAll();

    @Select("SELECT * FROM finance WHERE id = #{id}")
    Finance findById(Integer id);

    @Insert("INSERT INTO finance (date, revenue, expense, profit, remark, create_time) VALUES (#{date}, #{revenue}, #{expense}, #{profit}, #{remark}, NOW())")
    int insert(Finance finance);

    @Update("UPDATE finance SET date=#{date}, revenue=#{revenue}, expense=#{expense}, profit=#{profit}, remark=#{remark} WHERE id=#{id}")
    int update(Finance finance);

    @Delete("DELETE FROM finance WHERE id=#{id}")
    int delete(Integer id);
} 