package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Finance;
import java.util.List;

public interface FinanceService {
    List<Finance> findAll();
    Finance findById(Integer id);
    int insert(Finance finance);
    int update(Finance finance);
    int delete(Integer id);
    // 可根据需要补充新增、修改、删除等方法
} 