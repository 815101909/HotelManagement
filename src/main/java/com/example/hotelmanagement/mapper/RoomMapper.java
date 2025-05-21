package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.Room;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface RoomMapper {
    @Select("SELECT * FROM room WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "roomNumber", column = "room_number"),
        @Result(property = "type", column = "type"),
        @Result(property = "status", column = "status"),
        @Result(property = "price", column = "price"),
        @Result(property = "floor", column = "floor"),
        @Result(property = "description", column = "description")
    })
    Room findById(Integer id);

    @Select("SELECT * FROM room")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "roomNumber", column = "room_number"),
        @Result(property = "type", column = "type"),
        @Result(property = "status", column = "status"),
        @Result(property = "price", column = "price"),
        @Result(property = "floor", column = "floor"),
        @Result(property = "description", column = "description")
    })
    List<Room> findAll();

    @Insert("INSERT INTO room (room_number, type, status, price, floor, description, create_time) VALUES (#{roomNumber}, #{type}, #{status}, #{price}, #{floor}, #{description}, NOW())")
    int insert(Room room);

    @Update("UPDATE room SET room_number=#{roomNumber}, type=#{type}, status=#{status}, price=#{price}, floor=#{floor}, description=#{description} WHERE id=#{id}")
    int update(Room room);

    @Delete("DELETE FROM room WHERE id=#{id}")
    int delete(Integer id);
} 