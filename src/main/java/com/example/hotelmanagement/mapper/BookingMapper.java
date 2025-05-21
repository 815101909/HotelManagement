package com.example.hotelmanagement.mapper;

import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.entity.BookingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface BookingMapper {
    @Select("SELECT * FROM booking")
    List<Booking> findAll();

    @Select("SELECT * FROM booking WHERE id = #{id}")
    Booking findById(Integer id);

    @Insert("INSERT INTO booking (customer_id, room_id, checkin_date, checkout_date, status, total_price, create_time, payment_status, synced_to_finance) VALUES (#{customerId}, #{roomId}, #{checkinDate}, #{checkoutDate}, #{status}, #{totalPrice}, #{createdAt}, #{paymentStatus}, #{syncedToFinance})")
    int insert(Booking booking);

    @Update("UPDATE booking SET customer_id=#{customerId}, room_id=#{roomId}, checkin_date=#{checkinDate}, checkout_date=#{checkoutDate}, status=#{status}, total_price=#{totalPrice}, payment_status=#{paymentStatus}, synced_to_finance=#{syncedToFinance} WHERE id=#{id}")
    int update(Booking booking);

    @Delete("DELETE FROM booking WHERE id=#{id}")
    int delete(Integer id);
    
    @Update("UPDATE booking SET payment_status=#{paymentStatus} WHERE id=#{id}")
    int updatePaymentStatus(@Param("id") Integer id, @Param("paymentStatus") String paymentStatus);
    
    @Update("UPDATE booking SET synced_to_finance=#{syncedToFinance} WHERE id=#{id}")
    int updateSyncStatus(@Param("id") Integer id, @Param("syncedToFinance") Boolean syncedToFinance);
    
    @Select("SELECT * FROM booking WHERE payment_status IN ('PAID', 'paid', '已付款') AND (synced_to_finance IS NULL OR synced_to_finance = 0)")
    List<Booking> findUnsyncedPaidBookings();

    List<BookingVO> findAllWithDetails();
} 