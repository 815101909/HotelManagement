package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.mapper.RoomMapper;
import com.example.hotelmanagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public Room findById(Integer id) {
        return roomMapper.findById(id);
    }

    @Override
    public List<Room> findAll() {
        return roomMapper.findAll();
    }

    @Override
    public int insert(Room room) {
        return roomMapper.insert(room);
    }

    @Override
    public int update(Room room) {
        return roomMapper.update(room);
    }

    @Override
    public int delete(Integer id) {
        return roomMapper.delete(id);
    }
} 