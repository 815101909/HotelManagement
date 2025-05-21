package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.entity.BookingVO;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.mapper.BookingMapper;
import com.example.hotelmanagement.service.BookingService;
import com.example.hotelmanagement.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import java.util.Date;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

@Service
public class BookingServiceImpl implements BookingService {
    // 添加SLF4J Logger
    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
    
    @Autowired
    private BookingMapper bookingMapper;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @PostConstruct
    public void initData() {
        try {
            logger.info("初始化订单表结构...");
            
            // 检查booking表的payment_status字段是否存在
            Boolean paymentStatusExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) > 0 FROM information_schema.columns " +
                "WHERE table_name = 'booking' AND column_name = 'payment_status'", Boolean.class);
            
            if (paymentStatusExists == null || !paymentStatusExists) {
                // 添加payment_status字段
                logger.info("添加payment_status字段到booking表");
                jdbcTemplate.execute("ALTER TABLE booking ADD COLUMN payment_status VARCHAR(20) DEFAULT 'unpaid'");
            } else {
                logger.debug("payment_status字段已存在");
            }

            // 检查booking表的synced_to_finance字段是否存在
            Boolean syncedToFinanceExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) > 0 FROM information_schema.columns " +
                "WHERE table_name = 'booking' AND column_name = 'synced_to_finance'", Boolean.class);
            
            if (syncedToFinanceExists == null || !syncedToFinanceExists) {
                // 添加synced_to_finance字段
                logger.info("添加synced_to_finance字段到booking表");
                jdbcTemplate.execute("ALTER TABLE booking ADD COLUMN synced_to_finance BOOLEAN DEFAULT false");
            } else {
                logger.debug("synced_to_finance字段已存在");
            }
            
            // 创建数据库触发器：当booking支付状态变更为非paid时，自动删除相关income记录
            try {
                // 检查触发器是否已存在
                Boolean triggerExists = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) > 0 FROM information_schema.triggers " +
                    "WHERE trigger_name = 'trg_delete_income_on_unpaid'", Boolean.class);
                
                if (triggerExists == null || !triggerExists) {
                    // 创建触发器
                    logger.info("创建自动删除income记录的触发器");
                    String triggerSql = 
                        "CREATE TRIGGER trg_delete_income_on_unpaid " +
                        "AFTER UPDATE ON booking " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "  IF (OLD.payment_status = 'paid' OR OLD.payment_status = 'PAID') AND " +
                        "     (NEW.payment_status <> 'paid' AND NEW.payment_status <> 'PAID') THEN " +
                        "    DELETE FROM income WHERE booking_id = NEW.id; " +
                        "  END IF; " +
                        "END;";
                    jdbcTemplate.execute(triggerSql);
                    logger.info("触发器创建成功");
                } else {
                    // 更新触发器
                    logger.info("更新自动删除income记录的触发器");
                    jdbcTemplate.execute("DROP TRIGGER IF EXISTS trg_delete_income_on_unpaid");
                    String triggerSql = 
                        "CREATE TRIGGER trg_delete_income_on_unpaid " +
                        "AFTER UPDATE ON booking " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "  IF NEW.payment_status <> 'paid' AND NEW.payment_status <> 'PAID' THEN " +
                        "    DELETE FROM income WHERE booking_id = NEW.id; " +
                        "  END IF; " +
                        "END;";
                    jdbcTemplate.execute(triggerSql);
                    logger.info("触发器更新成功");
                }
            } catch (Exception e) {
                logger.error("创建触发器失败: {}", e.getMessage(), e);
                
                // 如果触发器创建失败，尝试直接执行删除语句
                logger.info("尝试创建存储过程来替代触发器");
                try {
                    jdbcTemplate.execute("DROP PROCEDURE IF EXISTS sp_delete_income_for_booking");
                    String procSql = 
                        "CREATE PROCEDURE sp_delete_income_for_booking(IN booking_id INT) " +
                        "BEGIN " +
                        "  DELETE FROM income WHERE booking_id = booking_id; " +
                        "END;";
                    jdbcTemplate.execute(procSql);
                    logger.info("存储过程创建成功");
                } catch (Exception ex) {
                    logger.error("创建存储过程失败: {}", ex.getMessage(), ex);
                }
            }
            
            logger.info("订单表结构初始化完成");

        } catch (Exception e) {
            // 使用SLF4J记录异常
            logger.error("初始化订单数据失败: {}", e.getMessage(), e);
        }
    }

    @Override
    public List<Booking> findAll() {
        logger.debug("查询所有订单");
        return bookingMapper.findAll();
    }

    @Override
    public Booking findById(Integer id) {
        logger.debug("查询订单，ID: {}", id);
        return bookingMapper.findById(id);
    }

    @Override
    public int insert(Booking booking) {
        // 初始化同步状态为false（未同步）
        if (booking.getSyncedToFinance() == null) {
            booking.setSyncedToFinance(false);
        }
        logger.info("新增订单: roomId={}, customerId={}, 入住日期={}, 退房日期={}", 
                booking.getRoomId(), booking.getCustomerId(), 
                booking.getCheckinDate(), booking.getCheckoutDate());
        int result = bookingMapper.insert(booking);
        logger.debug("订单新增结果: {}, 生成ID: {}", result, booking.getId());
        
        // 如果新建订单已经是已付款状态，直接同步到财务系统
        if (result > 0 && booking.getId() != null && 
            ("PAID".equalsIgnoreCase(booking.getPaymentStatus()) || "paid".equalsIgnoreCase(booking.getPaymentStatus()))) {
            
            syncBookingToFinance(booking);
        }
        
        return result;
    }

    @Override
    public int update(Booking booking) {
        logger.info("更新订单信息，ID: {}", booking.getId());
        
        // 检查订单是否已付款但未同步
        boolean needsSync = false;
        if (booking.getId() != null) {
            Booking existingBooking = findById(booking.getId());
            if (existingBooking != null) {
                // 检查支付状态是否从未付款变为已付款
                String oldStatus = existingBooking.getPaymentStatus();
                String newStatus = booking.getPaymentStatus();
                
                if ((oldStatus == null || !oldStatus.equalsIgnoreCase("paid")) && 
                    newStatus != null && newStatus.equalsIgnoreCase("paid")) {
                    needsSync = true;
                }
            }
        }
        
        int result = bookingMapper.update(booking);
        
        if (result > 0) {
            logger.debug("订单更新成功: {}", booking.getId());
            
            // 如果需要同步到财务系统
            if (needsSync && booking.getId() != null) {
                syncBookingToFinance(booking);
            }
        } else {
            logger.warn("订单更新失败，可能订单不存在: {}", booking.getId());
        }
        
        return result;
    }

    @Override
    public int delete(Integer id) {
        logger.info("删除订单，ID: {}", id);
        int result = bookingMapper.delete(id);
        if (result > 0) {
            logger.debug("订单删除成功: {}", id);
        } else {
            logger.warn("订单删除失败，可能订单不存在: {}", id);
        }
        return result;
    }

    @Override
    public List<BookingVO> findAllWithDetails() {
        logger.debug("查询所有订单详细信息");
        return bookingMapper.findAllWithDetails();
    }
    
    @Override
    @Transactional
    public int updatePaymentStatus(Integer id, String paymentStatus) {
        logger.info("更新订单支付状态，ID: {}, 支付状态: {}", id, paymentStatus);
        
        // 获取订单原始状态
        Booking existingBooking = findById(id);
        if (existingBooking == null) {
            logger.warn("订单不存在，ID: {}", id);
            return 0;
        }
        
        String originalStatus = existingBooking.getPaymentStatus();
        logger.info("订单 {} 原始支付状态: {}", id, originalStatus);
        
        // 根据前端传递的支付状态值，转换成数据库中使用的值
        String dbStatus = paymentStatus;
        if ("已付款".equals(paymentStatus)) {
            dbStatus = "paid";
        } else if ("未付款".equals(paymentStatus)) {
            dbStatus = "unpaid";
        } else if ("部分付款".equals(paymentStatus)) {
            dbStatus = "partial";
        } else if ("已退款".equals(paymentStatus)) {
            dbStatus = "refunded";
        }
        
        boolean isCurrentStatusPaid = "paid".equalsIgnoreCase(dbStatus) || "PAID".equalsIgnoreCase(dbStatus);
        boolean wasPaid = "paid".equalsIgnoreCase(originalStatus) || "PAID".equalsIgnoreCase(originalStatus);
        
        logger.debug("转换后的支付状态值: {}", dbStatus);
        
        // 先更新订单状态
        int result = bookingMapper.updatePaymentStatus(id, dbStatus);
        
        if (result > 0) {
            logger.info("订单 {} 支付状态已更新为 {}", id, dbStatus);
            
            // 当支付状态更新为已付款时，自动同步到财务系统
            if (isCurrentStatusPaid) {
                logger.info("订单 {} 更新为已付款状态，准备同步到财务系统", id);
                syncBookingToFinance(existingBooking);
            }
            // 如果状态从已付款变为其他状态，强制删除income记录
            else if (wasPaid) {
                logger.warn("订单 {} 从已付款状态变为{}，正在强制删除相关收入记录", id, dbStatus);
                
                // 方法1: 直接SQL删除
                try {
                    int deleted = jdbcTemplate.update("DELETE FROM income WHERE booking_id = ?", id);
                    logger.warn("直接删除结果: 已删除 {} 条收入记录", deleted);
                } catch (Exception e) {
                    logger.error("直接删除收入记录失败: {}", e.getMessage(), e);
                }
                
                // 方法2: 尝试调用存储过程
                try {
                    jdbcTemplate.update("CALL sp_delete_income_for_booking(?)", id);
                    logger.warn("通过存储过程删除收入记录完成");
                } catch (Exception e) {
                    logger.error("调用存储过程删除失败: {}", e.getMessage(), e);
                }
                
                // 方法3: 使用通用SQL删除，不使用参数化查询以避免可能的类型问题
                try {
                    String sql = "DELETE FROM income WHERE booking_id = " + id;
                    jdbcTemplate.execute(sql);
                    logger.warn("执行原生SQL删除完成");
                } catch (Exception e) {
                    logger.error("执行原生SQL删除失败: {}", e.getMessage(), e);
                }
                
                // 检查结果
                try {
                    Integer count = jdbcTemplate.queryForObject(
                        "SELECT COUNT(*) FROM income WHERE booking_id = ?", 
                        Integer.class, id);
                    
                    if (count != null && count > 0) {
                        logger.error("删除失败，仍有 {} 条收入记录", count);
                    } else {
                        logger.info("成功删除所有相关收入记录");
                    }
                } catch (Exception e) {
                    logger.error("检查删除结果时出错: {}", e.getMessage(), e);
                }
                
                // 更新同步状态为false
                bookingMapper.updateSyncStatus(id, false);
            }
        } else {
            logger.warn("订单支付状态更新失败，ID: {}", id);
        }
        
        return result;
    }
    
    @Override
    @Transactional
    public int markAsSynced(Integer id) {
        logger.info("标记订单已同步到财务系统，ID: {}", id);
        int result = bookingMapper.updateSyncStatus(id, true);
        if (result > 0) {
            logger.debug("订单同步状态更新成功: {}", id);
        } else {
            logger.warn("订单同步状态更新失败，可能订单不存在: {}", id);
        }
        return result;
    }
    
    @Override
    public List<Booking> getUnsyncedPaidBookings() {
        logger.debug("查询未同步到财务系统的已付款订单");
        List<Booking> bookings = bookingMapper.findUnsyncedPaidBookings();
        logger.info("找到 {} 个未同步到财务系统的已付款订单", bookings.size());
        return bookings;
    }

    /**
     * 同步预订数据到财务系统
     * @param booking 预订对象
     * @return 是否同步成功
     */
    private boolean syncBookingToFinance(Booking booking) {
        if (booking == null || booking.getId() == null) {
            logger.warn("无法同步预订到财务系统，预订对象无效");
            return false;
        }
        
        try {
            // 首先检查是否已经存在该预订的收入记录
            Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM income WHERE booking_id = ?", 
                Integer.class, 
                booking.getId());
                
            if (count != null && count > 0) {
                logger.info("订单 {} 已存在收入记录，不重复创建", booking.getId());
                // 更新同步状态以确保一致性
                bookingMapper.updateSyncStatus(booking.getId(), true);
                return true;
            }
            
            // 获取房间信息
            Room room = null;
            try {
                // 尝试通过ApplicationContext获取RoomService
                if (applicationContext != null) {
                    room = applicationContext.getBean(RoomService.class).getRoomById(booking.getRoomId());
                }
            } catch (Exception e) {
                logger.warn("获取RoomService失败，将使用默认值: {}", e.getMessage());
            }
            
            // 准备收入数据
            String roomType = (room != null && room.getType() != null) ? room.getType() : "标准房";
            String description = "预订ID: " + booking.getId();
            if (room != null && room.getDescription() != null) {
                description += " - " + room.getDescription();
            }
            Double amount = booking.getTotalPrice();
            if (amount == null) amount = 0.0;
            
            // 统一使用createdAt作为收入日期
            Date bookingDate = booking.getCreatedAt();
            if (bookingDate == null) {
                bookingDate = new Date();
            }
            
            // 直接插入到income表
            String sql = "INSERT INTO income (date, category, description, amount, status, booking_id, synced) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            int inserted = jdbcTemplate.update(sql, 
                bookingDate,
                roomType,
                description,
                amount,
                "PAID",
                booking.getId(),
                true
            );
            
            if (inserted > 0) {
                // 标记该预订已同步到财务系统
                int updated = bookingMapper.updateSyncStatus(booking.getId(), true);
                if (updated > 0) {
                    logger.info("订单 {} 已成功同步到财务系统", booking.getId());
                    return true;
                } else {
                    logger.error("更新订单 {} 同步状态失败", booking.getId());
                }
            } else {
                logger.error("订单 {} 同步到财务系统失败: 插入数据库失败", booking.getId());
            }
        } catch (Exception e) {
            logger.error("订单 {} 同步到财务系统时出错: {}", booking.getId(), e.getMessage(), e);
        }
        
        return false;
    }

    /**
     * 强制删除与预订关联的收入记录
     * 使用直接JDBC连接以避免事务和缓存问题
     * @param bookingId 预订ID
     * @return 是否删除成功
     */
    public boolean forceDeleteIncomeRecords(Integer bookingId) {
        logger.warn("开始强制删除订单ID {} 的所有收入记录", bookingId);
        
        if (bookingId == null) {
            logger.error("订单ID为空，无法删除收入记录");
            return false;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            // 获取数据源
            DataSource dataSource = jdbcTemplate.getDataSource();
            if (dataSource == null) {
                logger.error("无法获取数据源");
                return false;
            }
            
            // 获取直接连接并关闭自动提交
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            
            // 打印表结构信息以辅助调试
            try {
                List<Map<String, Object>> columns = jdbcTemplate.queryForList(
                    "SHOW COLUMNS FROM income");
                logger.info("income表结构: {}", columns);
            } catch (Exception e) {
                logger.error("获取表结构失败: {}", e.getMessage());
            }
            
            // 先查询是否存在相关记录
            List<Map<String, Object>> records = jdbcTemplate.queryForList(
                "SELECT * FROM income WHERE booking_id = ?", bookingId);
            
            if (records.isEmpty()) {
                logger.info("没有找到订单 {} 相关的收入记录", bookingId);
            } else {
                logger.info("找到 {} 条与订单 {} 相关的收入记录: {}", records.size(), bookingId, records);
                
                // 使用直接SQL连接删除记录
                stmt = conn.prepareStatement("DELETE FROM income WHERE booking_id = ?");
                stmt.setInt(1, bookingId);
                int deleted = stmt.executeUpdate();
                
                logger.warn("直接连接删除: 已删除 {} 条收入记录", deleted);
                
                // 强制提交更改
                conn.commit();
                
                // 验证记录是否已删除
                List<Map<String, Object>> remainingRecords = jdbcTemplate.queryForList(
                    "SELECT * FROM income WHERE booking_id = ?", bookingId);
                
                if (remainingRecords.isEmpty()) {
                    logger.info("确认所有记录已删除");
                    success = true;
                } else {
                    logger.error("删除失败! 仍有 {} 条记录", remainingRecords.size());
                    // 尝试其他可能的字段名
                    tryAlternativeDelete(conn, bookingId);
                }
            }
        } catch (SQLException e) {
            logger.error("删除收入记录时发生SQL错误: {}", e.getMessage(), e);
            // 尝试回滚
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {}
            }
        } catch (Exception e) {
            logger.error("删除收入记录时发生未知错误: {}", e.getMessage(), e);
        } finally {
            // 关闭资源
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) {}
            }
            if (conn != null) {
                try { 
                    conn.setAutoCommit(true);
                    conn.close(); 
                } catch (SQLException e) {}
            }
        }
        
        return success;
    }

    /**
     * 尝试使用其他可能的字段名删除记录
     */
    private void tryAlternativeDelete(Connection conn, Integer bookingId) {
        PreparedStatement stmt = null;
        
        try {
            // 尝试其他可能的字段名
            String[] possibleFields = {"booking", "bookingId", "order_id", "orderId", "id"};
            
            for (String field : possibleFields) {
                try {
                    stmt = conn.prepareStatement("DELETE FROM income WHERE " + field + " = ?");
                    stmt.setInt(1, bookingId);
                    int deleted = stmt.executeUpdate();
                    logger.info("使用字段 {} 删除: 已删除 {} 条记录", field, deleted);
                    conn.commit();
                    
                    if (deleted > 0) {
                        break;
                    }
                } catch (Exception e) {
                    logger.debug("尝试字段 {} 失败: {}", field, e.getMessage());
                } finally {
                    if (stmt != null) {
                        try { stmt.close(); } catch (SQLException e) {}
                        stmt = null;
                    }
                }
            }
            
        } catch (Exception e) {
            logger.error("尝试替代删除方法时发生错误: {}", e.getMessage());
        } finally {
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) {}
            }
        }
    }
} 