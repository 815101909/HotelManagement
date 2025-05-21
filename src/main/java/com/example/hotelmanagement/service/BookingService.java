package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.entity.BookingVO;
import java.util.List;

public interface BookingService {
    List<Booking> findAll();
    Booking findById(Integer id);
    int insert(Booking booking);
    int update(Booking booking);
    int delete(Integer id);
    List<BookingVO> findAllWithDetails();
    // 更新支付状态
    int updatePaymentStatus(Integer id, String paymentStatus);
    
    // 标记预订已同步到财务系统
    int markAsSynced(Integer id);
    
    // 获取未同步的已付款订单
    List<Booking> getUnsyncedPaidBookings();
    
    // 添加兼容FinanceController的方法
    default List<Booking> getAllBookings() {
        return findAll(); // 默认实现，直接调用已有的findAll方法
    }
    // 可根据需要补充新增、修改、删除等方法
} 