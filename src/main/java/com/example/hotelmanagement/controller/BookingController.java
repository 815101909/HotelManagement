package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.entity.BookingVO;
import com.example.hotelmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<BookingVO> getAllBookings() {
        return bookingService.findAllWithDetails();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Integer id) {
        return bookingService.findById(id);
    }

    @PostMapping
    public int addBooking(@RequestBody Booking booking) {
        return bookingService.insert(booking);
    }

    @PutMapping
    public int updateBooking(@RequestBody Booking booking) {
        return bookingService.update(booking);
    }

    @DeleteMapping("/{id}")
    public int deleteBooking(@PathVariable Integer id) {
        return bookingService.delete(id);
    }
    
    @PutMapping("/{id}/payment")
    public int updatePaymentStatus(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
        return bookingService.updatePaymentStatus(id, payload.get("paymentStatus"));
    }
} 