package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.service.RoomService;
import com.example.hotelmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.findAll();
    }
    
    @GetMapping("/stats")
    public Map<String, Object> getRoomStats() {
        List<Room> rooms = roomService.findAll();
        
        // For dashboard statistics
        Map<String, Object> stats = new HashMap<>();
        List<Booking> activeBookings = bookingService.findAll().stream()
                .filter(booking -> booking != null && "confirmed".equals(booking.getStatus()) || "checked-in".equals(booking.getStatus()))
                .collect(Collectors.toList());
        
        int totalRooms = rooms.size();
        int bookedRooms = activeBookings.size();
        int availableRooms = totalRooms - bookedRooms;
        
        stats.put("total", totalRooms);
        stats.put("booked", bookedRooms);
        stats.put("available", availableRooms);
        
        return stats;
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Integer id) {
        return roomService.findById(id);
    }

    @PostMapping
    public int addRoom(@RequestBody Room room) {
        return roomService.insert(room);
    }

    @PutMapping
    public int updateRoom(@RequestBody Room room) {
        return roomService.update(room);
    }

    @DeleteMapping("/{id}")
    public int deleteRoom(@PathVariable Integer id) {
        return roomService.delete(id);
    }
} 