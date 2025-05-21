package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Room;
import java.util.List;

public interface RoomService {
    Room findById(Integer id);
    List<Room> findAll();
    int insert(Room room);
    int update(Room room);
    int delete(Integer id);
    
    // 添加兼容FinanceController的方法
    default Room getRoomById(Integer id) {
        return findById(id); // 默认实现，直接调用已有的findById方法
    }
} 