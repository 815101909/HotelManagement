package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.entity.Expense;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.service.BookingService;
import com.example.hotelmanagement.service.ExpenseService;
import com.example.hotelmanagement.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 财务报表控制器
 */
@RestController
@RequestMapping("/api/admin/finance")
public class FinanceController {
    
    // 添加SLF4J Logger
    private static final Logger logger = LoggerFactory.getLogger(FinanceController.class);
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private ExpenseService expenseService;
    
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 获取财务明细数据，包括收入（已付款订单）和支出
     *
     * @param startDate 开始日期（可选）
     * @param endDate   结束日期（可选）
     * @return 财务明细列表
     */
    @GetMapping("/details")
    public ResponseEntity<Map<String, Object>> getFinanceDetails(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        
        // 日志
        logger.info("\n\n=================== 获取财务详情 ===================");
        logger.info("收到前端参数: startDate={}, endDate={}", startDate, endDate);

        // 修正startDate为当天00:00:00
        if (startDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            startDate = cal.getTime();
        }
        // 修正endDate为当天23:59:59，确保包含当天所有数据
        if (endDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            endDate = cal.getTime();
        }

        // 如果startDate和endDate相等，自动将endDate加一天，实现单天筛选
        if (startDate != null && endDate != null && startDate.equals(endDate)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DATE, 1);
            endDate = cal.getTime();
        }
        
        // 结果容器
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> detailsList = new ArrayList<>();
        
        // 获取所有预订（收入）
        List<Booking> allBookings = bookingService.getAllBookings();
        logger.info("获取到 {} 条预订记录", allBookings.size());
        
        // 调试输出所有预订信息
        for (Booking booking : allBookings) {
            logger.debug("预订ID: {}, 房间ID: {}, 付款状态: {}, 金额: {}, 日期: {}", 
                booking.getId(), booking.getRoomId(), booking.getPaymentStatus(), booking.getTotalPrice(), booking.getCreateTime());
        }
        
        // 筛选已付款预订
        List<Booking> paidBookings = allBookings.stream()
                .filter(booking -> {
                    String status = booking.getPaymentStatus();
                    // 支持多种付款状态的写法
                    boolean isPaid = (status != null) && 
                            (status.equalsIgnoreCase("PAID") || 
                             status.equalsIgnoreCase("paid") || 
                             status.equals("已付款") ||
                             status.contains("paid") ||
                             status.contains("PAID"));
                    
                    logger.debug("检查预订ID={}, 状态=[{}], 是否已付款: {}", booking.getId(), status, isPaid);
                    return isPaid;
                })
                .collect(Collectors.toList());
        
        logger.info("筛选出 {} 条已付款预订", paidBookings.size());
        
        // 处理收入数据
        BigDecimal totalIncome = BigDecimal.ZERO;
        Map<String, BigDecimal> incomeByCategory = new HashMap<>();
        
        for (Booking booking : paidBookings) {
            // 过滤日期范围
            Date bookingDate = booking.getCreateTime();
            if (bookingDate == null) {
                bookingDate = booking.getCreatedAt(); // 备选日期字段
                logger.debug("使用备选日期字段 createdAt: {}", bookingDate);
            }
            if (bookingDate == null) {
                // 如果仍然没有日期，使用当前日期
                bookingDate = new Date();
                logger.debug("预订ID={} 没有日期，使用当前日期: {}", booking.getId(), bookingDate);
            }
            
            // 注意：为了确保所有预订都显示出来，初始化时我们不应该过滤日期
            boolean skipDueToDate = false;
            if (startDate != null && bookingDate.before(startDate)) {
                skipDueToDate = true;
                logger.debug("预订ID={} 日期早于筛选开始日期，跳过", booking.getId());
            }
            if (endDate != null && bookingDate.after(endDate)) {
                skipDueToDate = true;
                logger.debug("预订ID={} 日期晚于筛选结束日期，跳过", booking.getId());
            }
            
            if (skipDueToDate) {
                continue;
            }
            
            // 获取房间信息
            Room room = roomService.getRoomById(booking.getRoomId());
            String roomType = room != null ? room.getType() : "未知房型";
            if (roomType == null || roomType.isEmpty()) {
                roomType = "标准房";
            }
            
            String description = room != null ? room.getDescription() : (roomType + "收入");
            
            // 如果描述为空，设置默认描述
            if (description == null || description.trim().isEmpty()) {
                description = "预订ID: " + booking.getId() + " - " + roomType + "收入";
            }
            
            // 计算金额
            BigDecimal amount = BigDecimal.ZERO;
            if (booking.getTotalPrice() != null) {
                amount = BigDecimal.valueOf(booking.getTotalPrice());
            }
            
            // 记录处理的预订信息
            logger.debug("处理预订收入 - ID: {}, 房间类型: {}, 描述: {}, 金额: {}, 日期: {}", 
                booking.getId(), roomType, description, amount, bookingDate);
            
            // 累计总收入
            totalIncome = totalIncome.add(amount);
            
            // 按房间类型累计收入
            if (!incomeByCategory.containsKey(roomType)) {
                incomeByCategory.put(roomType, BigDecimal.ZERO);
            }
            incomeByCategory.put(roomType, incomeByCategory.get(roomType).add(amount));
            
            // 添加到明细列表
            Map<String, Object> detail = new HashMap<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            detail.put("date", dateFormat.format(bookingDate));  // 使用精确到秒的时间格式
            detail.put("rawDate", bookingDate); // 保存原始Date对象，便于后续排序
            detail.put("category", roomType);
            detail.put("description", description);
            detail.put("income", amount);
            detail.put("expense", BigDecimal.ZERO);
            detailsList.add(detail);
            
            // 记录已添加到明细
            logger.debug("已将预订ID={} 添加到财务明细", booking.getId());
        }
        
        // 获取支出数据
        List<Expense> expenses = startDate != null && endDate != null ?
                expenseService.getExpensesByDateRange(startDate, endDate) :
                expenseService.getAllExpenses();
        
        // 处理支出数据
        BigDecimal totalExpense = BigDecimal.ZERO;
        Map<String, BigDecimal> expenseByCategory = new HashMap<>();
        
        for (Expense expense : expenses) {
            // 计算金额
            BigDecimal amount = expense.getAmount();
            if (amount == null) amount = BigDecimal.ZERO;
            
            // 累计总支出
            totalExpense = totalExpense.add(amount);
            
            // 按类别累计支出
            String category = expense.getCategory();
            if (category == null || category.trim().isEmpty()) {
                category = "其他";
            }
            
            if (!expenseByCategory.containsKey(category)) {
                expenseByCategory.put(category, BigDecimal.ZERO);
            }
            expenseByCategory.put(category, expenseByCategory.get(category).add(amount));
            
            // 获取描述
            String description = expense.getDescription();
            if (description == null || description.trim().isEmpty()) {
                description = category + "支出";
            }
            
            // 确保有日期
            Date expenseDate = expense.getExpenseDate();
            if (expenseDate == null) {
                expenseDate = expense.getCreateTime();
                if (expenseDate == null) {
                    expenseDate = new Date(); // 如果都没有，使用当前日期
                }
            }
            
            // 添加到明细列表
            Map<String, Object> detail = new HashMap<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            detail.put("date", dateFormat.format(expenseDate));  // 使用精确到秒的时间格式
            detail.put("rawDate", expenseDate); // 保存原始Date对象，便于后续排序
            detail.put("category", category);
            detail.put("description", description);
            detail.put("income", BigDecimal.ZERO);
            detail.put("expense", amount);
            detailsList.add(detail);
        }
        
        // 按日期排序明细列表（降序，最新的在前面）
        detailsList.sort((a, b) -> {
            Date dateA = (Date) a.get("rawDate");
            Date dateB = (Date) b.get("rawDate");
            return dateB.compareTo(dateA); // 使用原始Date对象进行排序
        });
        
        // 移除临时排序字段
        for (Map<String, Object> item : detailsList) {
            item.remove("rawDate");
        }
        
        // 计算净利润
        BigDecimal totalProfit = totalIncome.subtract(totalExpense);
        
        // 构建结果
        result.put("details", detailsList);
        result.put("totalIncome", totalIncome);
        result.put("totalExpense", totalExpense);
        result.put("totalProfit", totalProfit);
        result.put("incomeByCategory", incomeByCategory);
        result.put("expenseByCategory", expenseByCategory);
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * 获取月度趋势数据
     *
     * @return 月度收入趋势
     */
    @GetMapping("/trend/monthly")
    public ResponseEntity<List<Map<String, Object>>> getMonthlyTrend() {
        // 获取当前年份
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        
        // 获取所有已付款的预订
        List<Booking> paidBookings = bookingService.getAllBookings().stream()
                .filter(booking -> "PAID".equalsIgnoreCase(booking.getPaymentStatus()))
                .collect(Collectors.toList());
        
        // 按月统计收入
        Map<Integer, BigDecimal> monthlyIncome = new HashMap<>();
        for (int month = 1; month <= 12; month++) {
            monthlyIncome.put(month, BigDecimal.ZERO);
        }
        
        // 处理收入数据
        for (Booking booking : paidBookings) {
            Date createTime = booking.getCreateTime();
            if (createTime == null) continue;
            
            cal.setTime(createTime);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH是从0开始的
            
            // 只统计当年数据
            if (year == currentYear) {
                BigDecimal amount = booking.getTotalAmount();
                if (amount == null) amount = BigDecimal.ZERO;
                
                monthlyIncome.put(month, monthlyIncome.get(month).add(amount));
            }
        }
        
        // 构建结果
        List<Map<String, Object>> result = new ArrayList<>();
        String[] monthNames = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        
        for (int month = 1; month <= 12; month++) {
            Map<String, Object> item = new HashMap<>();
            item.put("month", monthNames[month - 1]);
            item.put("income", monthlyIncome.get(month));
            result.add(item);
        }
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * 获取收入来源分布数据
     *
     * @return 收入来源分布
     */
    @GetMapping("/income-sources")
    public ResponseEntity<List<Map<String, Object>>> getIncomeSources() {
        // 获取所有已付款的预订
        List<Booking> paidBookings = bookingService.getAllBookings().stream()
                .filter(booking -> "PAID".equalsIgnoreCase(booking.getPaymentStatus()))
                .collect(Collectors.toList());
        
        // 按房间类型统计收入
        Map<String, BigDecimal> incomeByType = new HashMap<>();
        BigDecimal totalIncome = BigDecimal.ZERO;
        
        for (Booking booking : paidBookings) {
            Room room = roomService.getRoomById(booking.getRoomId());
            String roomType = room != null ? room.getType() : "其他";
            
            BigDecimal amount = booking.getTotalAmount();
            if (amount == null) amount = BigDecimal.ZERO;
            
            // 累计总收入
            totalIncome = totalIncome.add(amount);
            
            // 按房间类型累计收入
            if (!incomeByType.containsKey(roomType)) {
                incomeByType.put(roomType, BigDecimal.ZERO);
            }
            incomeByType.put(roomType, incomeByType.get(roomType).add(amount));
        }
        
        // 计算百分比并构建结果
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (Map.Entry<String, BigDecimal> entry : incomeByType.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", entry.getKey());
            
            // 计算百分比（保留两位小数）
            BigDecimal percentage = BigDecimal.ZERO;
            if (totalIncome.compareTo(BigDecimal.ZERO) > 0) {
                percentage = entry.getValue()
                        .multiply(new BigDecimal("100"))
                        .divide(totalIncome, 2, BigDecimal.ROUND_HALF_UP);
            }
            
            item.put("percentage", percentage);
            item.put("value", entry.getValue());
            result.add(item);
        }
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * 记录房间收入
     *
     * @param incomeData 收入数据
     * @return 操作结果
     */
    @PostMapping("/income")
    public ResponseEntity<Map<String, Object>> recordIncome(@RequestBody Map<String, Object> incomeData) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 获取参数
            String roomType = String.valueOf(incomeData.get("roomType"));
            String description = String.valueOf(incomeData.get("description"));
            BigDecimal totalAmount = new BigDecimal(String.valueOf(incomeData.get("totalAmount")));
            String dateString = String.valueOf(incomeData.get("createTime"));
            
            // 创建Booking对象（模拟预订收入）
            Booking booking = new Booking();
            booking.setRoomId(1); // 默认roomId
            booking.setCustomerId(1); // 默认customerId
            
            // 设置日期
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date bookingDate;
            try {
                bookingDate = dateFormat.parse(dateString);
            } catch (Exception e) {
                bookingDate = new Date(); // 如果日期格式错误，使用当前日期
            }
            
            booking.setCreatedAt(bookingDate);
            booking.setCheckinDate(bookingDate);
            booking.setCheckoutDate(bookingDate); // 单日收入，入住和退房日期相同
            
            // 设置描述（如果存在房间则使用房间描述，否则使用提供的描述）
            Room room = null;
            List<Room> rooms = roomService.findAll();
            for (Room r : rooms) {
                if (r.getType() != null && r.getType().equals(roomType)) {
                    room = r;
                    break;
                }
            }
            
            // 设置房间ID（如果找到了符合类型的房间）
            if (room != null) {
                booking.setRoomId(room.getId());
            }
            
            // 设置价格
            booking.setTotalPrice(totalAmount.doubleValue());
            booking.setStatus("completed");
            booking.setPaymentStatus("PAID");
            
            // 保存预订记录
            bookingService.insert(booking);
            
            result.put("success", true);
            result.put("message", "房间收入记录成功");
            result.put("id", booking.getId());
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "处理房间收入时发生错误: " + e.getMessage());
            e.printStackTrace(); // 打印详细错误信息到控制台
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     * 同步预订数据到财务系统
     * 自动读取预订系统中的已付款订单，并将其计算为收入
     *
     * @return 操作结果
     */
    @PostMapping("/sync-bookings")
    public ResponseEntity<Map<String, Object>> syncBookingData() {
        Map<String, Object> result = new HashMap<>();
        int syncCount = 0;
        List<Map<String, Object>> syncedItems = new ArrayList<>();
        
        try {
            // 获取所有未同步的已付款预订
            List<Booking> unsyncedPaidBookings = bookingService.getUnsyncedPaidBookings();
            
            // 记录日志
            logger.info("找到 {} 条未同步的已付款预订记录", unsyncedPaidBookings.size());
            
            for (Booking booking : unsyncedPaidBookings) {
                // 获取房间信息
                Room room = roomService.getRoomById(booking.getRoomId());
                if (room == null) continue;
                
                // 准备收入数据
                String roomType = room.getType() != null ? room.getType() : "标准房";
                String description = "预订ID: " + booking.getId() + 
                        (room.getDescription() != null ? " - " + room.getDescription() : "");
                BigDecimal amount = booking.getTotalAmount();
                
                // 统一使用createdAt作为收入日期
                Date bookingDate = booking.getCreatedAt();
                if (bookingDate == null) {
                    bookingDate = new Date();
                }
                
                try {
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
                        bookingService.markAsSynced(booking.getId());
                        
                        // 准备返回数据
                        Map<String, Object> syncedItem = new HashMap<>();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        syncedItem.put("date", dateFormat.format(bookingDate));
                        syncedItem.put("category", roomType);
                        syncedItem.put("description", description);
                        syncedItem.put("income", amount);
                        syncedItem.put("expense", 0);
                        syncedItems.add(syncedItem);
                        
                        syncCount++;
                        logger.info("成功同步预订{}的收入: ¥{}", booking.getId(), amount);
                    } else {
                        logger.error("同步预订{}的收入失败: 插入数据库失败", booking.getId());
                    }
                } catch (Exception e) {
                    logger.error("处理预订{}时出错: {}", booking.getId(), e.getMessage());
                }
            }
            
            result.put("success", true);
            result.put("message", "成功同步 " + syncCount + " 条预订收入到财务系统");
            result.put("syncedItems", syncedItems);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "同步预订收入失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     * 获取收入数据
     *
     * @param startDate 开始日期（可选）
     * @param endDate   结束日期（可选）
     * @return 收入数据列表
     */
    @GetMapping("/income")
    public ResponseEntity<List<Map<String, Object>>> getIncome(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        
        List<Map<String, Object>> result = new ArrayList<>();
        
        try {
            // 构建查询参数
            StringBuilder sql = new StringBuilder("SELECT * FROM income WHERE 1=1");
            List<Object> params = new ArrayList<>();
            
            // 添加日期过滤条件
            if (startDate != null) {
                sql.append(" AND date >= ?");
                params.add(startDate);
            }
            
            if (endDate != null) {
                sql.append(" AND date <= ?");
                params.add(endDate);
            }
            
            // 添加排序
            sql.append(" ORDER BY date DESC");
            
            // 查询收入数据
            List<Map<String, Object>> incomeData = jdbcTemplate.queryForList(
                sql.toString(), 
                params.toArray()
            );
            
            // 处理查询结果
            for (Map<String, Object> item : incomeData) {
                Map<String, Object> incomeItem = new HashMap<>();
                
                // 处理日期
                Object dateObj = item.get("date");
                if (dateObj instanceof Date) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    incomeItem.put("date", dateFormat.format((Date) dateObj));
                } else if (dateObj != null) {
                    incomeItem.put("date", dateObj.toString());
                }
                
                // 复制其他字段
                incomeItem.put("category", item.get("category"));
                incomeItem.put("description", item.get("description"));
                incomeItem.put("amount", item.get("amount"));
                incomeItem.put("status", item.get("status"));
                incomeItem.put("booking_id", item.get("booking_id"));
                
                result.add(incomeItem);
            }
            
            logger.info("获取到{}条收入记录", result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error("获取收入数据失败: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    /**
     * 直接添加收入记录到收入表
     *
     * @param incomeData 收入数据
     * @return 操作结果
     */
    @PostMapping("/income/add")
    public ResponseEntity<Map<String, Object>> addIncome(@RequestBody Map<String, Object> incomeData) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            logger.info("接收到收入数据: {}", incomeData);
            
            // 获取参数
            String category = String.valueOf(incomeData.getOrDefault("category", "其他"));
            String description = String.valueOf(incomeData.getOrDefault("description", "收入记录"));
            BigDecimal amount = new BigDecimal(String.valueOf(incomeData.getOrDefault("amount", "0.0")));
            String status = String.valueOf(incomeData.getOrDefault("status", "PAID"));
            
            // 处理日期
            String dateString = String.valueOf(incomeData.get("date"));
            Date incomeDate;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                incomeDate = dateFormat.parse(dateString);
            } catch (Exception e) {
                logger.warn("日期格式转换失败，使用当前时间: {}", e.getMessage());
                incomeDate = new Date(); // 如果日期格式错误，使用当前日期
            }
            
            // 插入到income表
            String sql = "INSERT INTO income (date, category, description, amount, status, synced) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
            
            int inserted = jdbcTemplate.update(sql, 
                incomeDate,
                category,
                description,
                amount,
                status,
                true
            );
            
            if (inserted > 0) {
                // 构建返回结果
                result.put("success", true);
                result.put("message", "收入记录成功");
                
                // 构建返回的收入记录
                Map<String, Object> incomeRecord = new HashMap<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                incomeRecord.put("date", dateFormat.format(incomeDate));
                incomeRecord.put("category", category);
                incomeRecord.put("description", description);
                incomeRecord.put("amount", amount);
                incomeRecord.put("status", status);
                
                result.put("data", incomeRecord);
            } else {
                result.put("success", false);
                result.put("message", "收入记录失败，插入操作未成功");
            }
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error("处理收入记录时发生错误: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "处理收入记录时发生错误: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
