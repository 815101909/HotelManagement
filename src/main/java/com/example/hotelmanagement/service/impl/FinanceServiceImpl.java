package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Finance;
import com.example.hotelmanagement.mapper.FinanceMapper;
import com.example.hotelmanagement.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FinanceServiceImpl implements FinanceService {
    @Autowired
    private FinanceMapper financeMapper;

    @Override
    public List<Finance> findAll() {
        return financeMapper.findAll();
    }

    @Override
    public Finance findById(Integer id) {
        return financeMapper.findById(id);
    }

    @Override
    public int insert(Finance finance) {
        return financeMapper.insert(finance);
    }

    @Override
    public int update(Finance finance) {
        return financeMapper.update(finance);
    }

    @Override
    public int delete(Integer id) {
        return financeMapper.delete(id);
    }
} 