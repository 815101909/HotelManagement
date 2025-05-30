/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : db_hotel

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 31/05/2025 03:55:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '超级管理员', '13800000000', 'admin@hotel.com', '2025-05-16 02:40:50');

-- ----------------------------
-- Table structure for backup_history
-- ----------------------------
DROP TABLE IF EXISTS `backup_history`;
CREATE TABLE `backup_history`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `backup_time` datetime NOT NULL,
  `size` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of backup_history
-- ----------------------------
INSERT INTO `backup_history` VALUES (1, '2023-05-10 08:30:15', '25.4 MB', '自动', 'success', 'backup_20230510_083015.sql', '2025-05-30 18:00:01');
INSERT INTO `backup_history` VALUES (2, '2023-05-09 08:30:12', '25.2 MB', '自动', 'success', 'backup_20230509_083012.sql', '2025-05-30 18:00:01');
INSERT INTO `backup_history` VALUES (3, '2023-05-08 08:30:10', '25.0 MB', '自动', 'success', 'backup_20230508_083010.sql', '2025-05-30 18:00:01');
INSERT INTO `backup_history` VALUES (4, '2023-05-07 08:30:08', '24.8 MB', '自动', 'success', 'backup_20230507_083008.sql', '2025-05-30 18:00:01');
INSERT INTO `backup_history` VALUES (5, '2023-05-06 08:30:05', '24.5 MB', '自动', 'success', 'backup_20230506_083005.sql', '2025-05-30 18:00:01');
INSERT INTO `backup_history` VALUES (6, '2023-05-05 14:15:30', '24.3 MB', '手动', 'success', 'backup_20230505_141530.sql', '2025-05-30 18:00:01');
INSERT INTO `backup_history` VALUES (7, '2023-05-05 08:30:00', '24.2 MB', '自动', 'failed', 'backup_20230505_083000.sql', '2025-05-30 18:00:01');

-- ----------------------------
-- Table structure for backup_settings
-- ----------------------------
DROP TABLE IF EXISTS `backup_settings`;
CREATE TABLE `backup_settings`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `auto_backup` tinyint(1) NULL DEFAULT 0,
  `frequency` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'daily',
  `retention` int NULL DEFAULT 7,
  `last_backup_time` datetime NULL DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'success',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of backup_settings
-- ----------------------------
INSERT INTO `backup_settings` VALUES (1, 1, 'daily', 7, '2023-05-10 08:30:15', 'success', '2025-05-30 18:39:33');

-- ----------------------------
-- Table structure for booking
-- ----------------------------
DROP TABLE IF EXISTS `booking`;
CREATE TABLE `booking`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `room_id` int NOT NULL,
  `checkin_date` date NULL DEFAULT NULL,
  `checkout_date` date NULL DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `total_price` decimal(10, 2) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `payment_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '未付款',
  `synced_to_finance` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `customer_id`(`customer_id` ASC) USING BTREE,
  INDEX `room_id`(`room_id` ASC) USING BTREE,
  INDEX `idx_booking_status`(`status` ASC) USING BTREE,
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of booking
-- ----------------------------
INSERT INTO `booking` VALUES (28, 5, 1, '2024-12-20', '2025-05-22', 'checked-out', 400.00, '2025-05-20 21:53:42', 'paid', 1);
INSERT INTO `booking` VALUES (29, 7, 2, '2025-01-20', '2025-05-23', 'checked-out', 900.00, '2025-05-20 21:54:27', 'paid', 1);
INSERT INTO `booking` VALUES (30, 1, 3, '2025-04-18', '2025-05-20', 'checked-out', 1000.00, '2025-05-20 21:55:17', 'paid', 1);
INSERT INTO `booking` VALUES (31, 6, 1, '2025-05-16', '2025-05-20', 'checked-out', 800.00, '2025-05-20 21:55:46', 'paid', 1);
INSERT INTO `booking` VALUES (32, 1, 4, '2025-05-21', '2025-05-23', 'checked-out', 598.00, '2025-05-20 21:56:23', 'paid', 1);
INSERT INTO `booking` VALUES (33, 4, 3, '2025-05-24', '2025-05-28', 'checked-in', 2000.00, '2025-05-24 03:02:09', 'paid', 1);
INSERT INTO `booking` VALUES (34, 5, 2, '2025-05-24', '2025-05-26', 'checked-out', 300.00, '2025-05-24 03:19:59', 'paid', 1);
INSERT INTO `booking` VALUES (35, 1, 1, '2025-05-24', '2025-05-29', 'checked-out', 1200.00, '2025-05-24 14:51:00', 'paid', 1);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `id_card` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'regular',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'active',
  `id_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_customer_phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, '赵六', '男', '13811112222', '110101199001010011', 'zhaoliu@example.com', '2025-05-16 02:40:50', 'vip', 'active', NULL, NULL, NULL);
INSERT INTO `customer` VALUES (2, '孙七', '女', '13833334441', '110101199202020022', 'sunqi@example.com', '2025-05-16 02:40:50', 'regular', 'active', NULL, NULL, NULL);
INSERT INTO `customer` VALUES (3, 'test', NULL, '13833334444', NULL, 'sunqi@example.com', '2025-05-16 22:54:59', 'regular', 'active', NULL, NULL, NULL);
INSERT INTO `customer` VALUES (4, '张三', NULL, NULL, NULL, NULL, '2025-05-18 16:41:12', 'regular', NULL, NULL, NULL, NULL);
INSERT INTO `customer` VALUES (5, '李四', NULL, '13800138004', NULL, 'lisi@example.com', '2025-05-19 22:03:32', 'regular', 'active', NULL, NULL, NULL);
INSERT INTO `customer` VALUES (6, '孙琪', NULL, NULL, NULL, NULL, '2025-05-20 00:27:44', 'regular', NULL, NULL, NULL, NULL);
INSERT INTO `customer` VALUES (7, '王五', NULL, NULL, NULL, NULL, '2025-05-20 21:52:47', 'regular', NULL, NULL, NULL, NULL);
INSERT INTO `customer` VALUES (8, '李斯', NULL, NULL, NULL, NULL, '2025-05-20 21:57:07', 'regular', NULL, NULL, NULL, NULL);
INSERT INTO `customer` VALUES (9, '阿巴斯', NULL, NULL, NULL, NULL, '2025-05-24 02:40:21', 'regular', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `position` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `hire_date` date NULL DEFAULT NULL,
  `salary` decimal(10, 2) NULL DEFAULT NULL,
  `status` tinyint NULL DEFAULT 1,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '张三', '男', '13900000001', '前台', '2022-01-10', 4000.00, 1, '2025-05-16 02:40:50');
INSERT INTO `employee` VALUES (2, '李四', '女', '13900000002', '保洁', '2021-05-15', 3500.00, 1, '2025-05-16 02:40:50');
INSERT INTO `employee` VALUES (3, '王五', '男', '13900000003', '维修', '2020-09-20', 4200.00, 0, '2025-05-16 02:40:50');

-- ----------------------------
-- Table structure for expense
-- ----------------------------
DROP TABLE IF EXISTS `expense`;
CREATE TABLE `expense`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支出类别',
  `amount` decimal(10, 2) NOT NULL COMMENT '支出金额',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支出描述',
  `expense_date` date NOT NULL COMMENT '支出日期',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支出记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of expense
-- ----------------------------
INSERT INTO `expense` VALUES (1, '人力', 111.00, '搬运货物', '2025-05-19', '2025-05-19 19:49:23', '2025-05-19 19:49:23', '');
INSERT INTO `expense` VALUES (2, '水电', 1000.00, 'test', '2025-05-19', '2025-05-19 22:17:56', '2025-05-19 22:17:56', '');
INSERT INTO `expense` VALUES (3, '餐饮', 1000.00, '员工餐补', '2025-05-19', '2025-05-19 22:25:35', '2025-05-19 22:25:35', '');
INSERT INTO `expense` VALUES (8, '设施', 500.00, '公共设施', '2025-05-20', '2025-05-19 23:19:22', '2025-05-19 23:19:22', '');
INSERT INTO `expense` VALUES (9, '人力', 500.00, '搬运货物', '2025-05-21', '2025-05-20 23:43:20', '2025-05-20 23:43:20', '');
INSERT INTO `expense` VALUES (11, '人力', 200.00, '200', '2025-05-21', '2025-05-21 00:13:10', '2025-05-21 00:13:10', '');
INSERT INTO `expense` VALUES (12, '水电', 100.00, '100', '2025-05-21', '2025-05-21 00:44:36', '2025-05-21 00:44:36', '');
INSERT INTO `expense` VALUES (13, '水电', 100.00, 'test', '2025-05-21', '2025-05-21 16:05:21', '2025-05-21 16:05:21', '');
INSERT INTO `expense` VALUES (14, '人力', 1.00, '测试日期是否精确到当前时间', '2025-05-21', '2025-05-21 16:14:27', '2025-05-21 16:14:27', '');
INSERT INTO `expense` VALUES (15, '人力', 1.00, '测试时间', '2025-05-21', '2025-05-21 17:03:29', '2025-05-21 17:03:29', '');
INSERT INTO `expense` VALUES (21, '人力', 1.00, '测试筛选今日数据', '2025-05-26', '2025-05-26 16:15:23', '2025-05-26 16:15:23', '');
INSERT INTO `expense` VALUES (22, '水电', 1.00, 'test', '2025-05-29', '2025-05-29 01:04:49', '2025-05-29 01:04:49', '');

-- ----------------------------
-- Table structure for general_settings
-- ----------------------------
DROP TABLE IF EXISTS `general_settings`;
CREATE TABLE `general_settings`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `hotel_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `hotel_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `hotel_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `hotel_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `hotel_website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `currency` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'CNY',
  `timezone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Asia/Shanghai',
  `language` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'zh-CN',
  `theme` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'light',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of general_settings
-- ----------------------------
INSERT INTO `general_settings` VALUES (1, '儒家酒店', '北京市朝阳区建国路88号', '010-12345678', 'info@example.com', 'https://www.example.com', 'CNY', 'Asia/Shanghai', 'zh-CN', 'light', '2025-05-31 01:35:45');

-- ----------------------------
-- Table structure for income
-- ----------------------------
DROP TABLE IF EXISTS `income`;
CREATE TABLE `income`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL COMMENT '收入日期时间',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收入类别或房间类型',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收入描述',
  `amount` decimal(10, 2) NOT NULL COMMENT '收入金额',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'PAID' COMMENT '付款状态',
  `booking_id` int NULL DEFAULT NULL COMMENT '关联预订ID',
  `synced` tinyint(1) NULL DEFAULT 1 COMMENT '是否已同步至财务系统',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_date`(`date` ASC) USING BTREE,
  INDEX `idx_category`(`category` ASC) USING BTREE,
  INDEX `idx_booking_id`(`booking_id` ASC) USING BTREE,
  CONSTRAINT `income_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收入记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of income
-- ----------------------------
INSERT INTO `income` VALUES (3, '2025-05-20 23:06:07', '单人间', '预订ID: 28 - 舒适单人间', 400.00, 'PAID', 28, 1, '2025-05-20 23:06:06', '2025-05-20 23:06:06');
INSERT INTO `income` VALUES (4, '2025-05-20 23:06:13', '双人间', '预订ID: 29 - 宽敞双人间', 900.00, 'PAID', 29, 1, '2025-05-20 23:06:12', '2025-05-20 23:06:12');
INSERT INTO `income` VALUES (5, '2025-05-20 23:06:15', '套房', '预订ID: 30 - 豪华套房', 1000.00, 'PAID', 30, 1, '2025-05-20 23:06:15', '2025-05-20 23:06:15');
INSERT INTO `income` VALUES (13, '2025-05-24 03:12:44', '单人间', '预订ID: 31 - 舒适单人间', 800.00, 'PAID', 31, 1, '2025-05-24 03:12:44', '2025-05-24 03:12:44');
INSERT INTO `income` VALUES (14, '2025-05-24 03:12:50', '标准间', '预订ID: 32 - 总统套房', 598.00, 'PAID', 32, 1, '2025-05-24 03:12:50', '2025-05-24 03:12:50');
INSERT INTO `income` VALUES (15, '2025-05-24 03:12:53', '套房', '预订ID: 33 - 豪华套房', 2000.00, 'PAID', 33, 1, '2025-05-24 03:12:53', '2025-05-24 03:12:53');
INSERT INTO `income` VALUES (16, '2025-05-24 16:08:18', '单人间', '预订ID: 35 - 舒适单人间', 1200.00, 'PAID', 35, 1, '2025-05-24 16:08:17', '2025-05-24 16:08:17');
INSERT INTO `income` VALUES (17, '2025-05-24 16:29:05', '双人间', '预订ID: 34 - 宽敞双人间', 300.00, 'PAID', 34, 1, '2025-05-24 16:29:04', '2025-05-24 16:29:04');
INSERT INTO `income` VALUES (18, '2025-05-26 23:10:30', '双人间', '预订ID: 34 - 宽敞双人间', 300.00, 'PAID', 34, 1, '2025-05-26 23:10:30', '2025-05-26 23:10:30');
INSERT INTO `income` VALUES (19, '2025-05-29 01:59:33', '单人间', '预订ID: 35 - 舒适单人间', 1200.00, 'PAID', 35, 1, '2025-05-29 01:59:33', '2025-05-29 01:59:33');

-- ----------------------------
-- Table structure for notification_settings
-- ----------------------------
DROP TABLE IF EXISTS `notification_settings`;
CREATE TABLE `notification_settings`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `email_enabled` tinyint(1) NULL DEFAULT 0,
  `email_server` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email_port` int NULL DEFAULT NULL,
  `email_username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email_from` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sms_enabled` tinyint(1) NULL DEFAULT 0,
  `sms_provider` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sms_api_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sms_api_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `event_booking` tinyint(1) NULL DEFAULT 0,
  `event_checkin` tinyint(1) NULL DEFAULT 0,
  `event_checkout` tinyint(1) NULL DEFAULT 0,
  `event_payment` tinyint(1) NULL DEFAULT 0,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notification_settings
-- ----------------------------
INSERT INTO `notification_settings` VALUES (1, 1, 'smtp.example.com', 587, 'notifications@example.com', 'your_password', 'hotel@example.com', 0, 'aliyun', '', '', 1, 1, 1, 1, '2025-05-30 17:59:55');

-- ----------------------------
-- Table structure for role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `role_permissions`;
CREATE TABLE `role_permissions`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `permission_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role`(`role` ASC, `permission_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permissions
-- ----------------------------
INSERT INTO `role_permissions` VALUES (24, 'admin', 'backup.manage');
INSERT INTO `role_permissions` VALUES (8, 'admin', 'bookings.add');
INSERT INTO `role_permissions` VALUES (11, 'admin', 'bookings.checkin');
INSERT INTO `role_permissions` VALUES (12, 'admin', 'bookings.checkout');
INSERT INTO `role_permissions` VALUES (10, 'admin', 'bookings.delete');
INSERT INTO `role_permissions` VALUES (9, 'admin', 'bookings.edit');
INSERT INTO `role_permissions` VALUES (7, 'admin', 'bookings.view');
INSERT INTO `role_permissions` VALUES (14, 'admin', 'customers.add');
INSERT INTO `role_permissions` VALUES (16, 'admin', 'customers.delete');
INSERT INTO `role_permissions` VALUES (15, 'admin', 'customers.edit');
INSERT INTO `role_permissions` VALUES (13, 'admin', 'customers.view');
INSERT INTO `role_permissions` VALUES (2, 'admin', 'dashboard.analytics');
INSERT INTO `role_permissions` VALUES (1, 'admin', 'dashboard.view');
INSERT INTO `role_permissions` VALUES (19, 'admin', 'finance.reports');
INSERT INTO `role_permissions` VALUES (18, 'admin', 'finance.transactions');
INSERT INTO `role_permissions` VALUES (17, 'admin', 'finance.view');
INSERT INTO `role_permissions` VALUES (23, 'admin', 'permissions.manage');
INSERT INTO `role_permissions` VALUES (4, 'admin', 'rooms.add');
INSERT INTO `role_permissions` VALUES (6, 'admin', 'rooms.delete');
INSERT INTO `role_permissions` VALUES (5, 'admin', 'rooms.edit');
INSERT INTO `role_permissions` VALUES (3, 'admin', 'rooms.view');
INSERT INTO `role_permissions` VALUES (21, 'admin', 'settings.edit');
INSERT INTO `role_permissions` VALUES (20, 'admin', 'settings.view');
INSERT INTO `role_permissions` VALUES (22, 'admin', 'users.manage');
INSERT INTO `role_permissions` VALUES (31, 'manager', 'bookings.add');
INSERT INTO `role_permissions` VALUES (33, 'manager', 'bookings.checkin');
INSERT INTO `role_permissions` VALUES (34, 'manager', 'bookings.checkout');
INSERT INTO `role_permissions` VALUES (32, 'manager', 'bookings.edit');
INSERT INTO `role_permissions` VALUES (30, 'manager', 'bookings.view');
INSERT INTO `role_permissions` VALUES (36, 'manager', 'customers.add');
INSERT INTO `role_permissions` VALUES (37, 'manager', 'customers.edit');
INSERT INTO `role_permissions` VALUES (35, 'manager', 'customers.view');
INSERT INTO `role_permissions` VALUES (26, 'manager', 'dashboard.analytics');
INSERT INTO `role_permissions` VALUES (25, 'manager', 'dashboard.view');
INSERT INTO `role_permissions` VALUES (39, 'manager', 'finance.reports');
INSERT INTO `role_permissions` VALUES (38, 'manager', 'finance.view');
INSERT INTO `role_permissions` VALUES (28, 'manager', 'rooms.add');
INSERT INTO `role_permissions` VALUES (29, 'manager', 'rooms.edit');
INSERT INTO `role_permissions` VALUES (27, 'manager', 'rooms.view');
INSERT INTO `role_permissions` VALUES (40, 'manager', 'settings.view');
INSERT INTO `role_permissions` VALUES (44, 'staff', 'bookings.add');
INSERT INTO `role_permissions` VALUES (45, 'staff', 'bookings.checkin');
INSERT INTO `role_permissions` VALUES (46, 'staff', 'bookings.checkout');
INSERT INTO `role_permissions` VALUES (43, 'staff', 'bookings.view');
INSERT INTO `role_permissions` VALUES (48, 'staff', 'customers.add');
INSERT INTO `role_permissions` VALUES (47, 'staff', 'customers.view');
INSERT INTO `role_permissions` VALUES (41, 'staff', 'dashboard.view');
INSERT INTO `role_permissions` VALUES (42, 'staff', 'rooms.view');

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `floor` int NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `room_number`(`room_number` ASC) USING BTREE,
  INDEX `idx_room_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES (1, '101', '单人间', '空闲', 200.00, 1, '舒适单人间', '2025-05-16 02:40:50');
INSERT INTO `room` VALUES (2, '102', '双人间', '已预订', 300.00, 1, '宽敞双人间', '2025-05-16 02:40:50');
INSERT INTO `room` VALUES (3, '201', '套房', '空闲', 500.00, 2, '豪华套房', '2025-05-16 02:40:50');
INSERT INTO `room` VALUES (4, '202', '标准间', '空闲', 299.00, 1, '总统套房', '2025-05-16 04:12:36');

-- ----------------------------
-- View structure for room_status_calendar
-- ----------------------------
DROP VIEW IF EXISTS `room_status_calendar`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `room_status_calendar` AS select `r`.`room_id` AS `room_id`,`r`.`room_number` AS `room_number`,`c`.`checkin_date` AS `checkin_date`,`c`.`checkout_date` AS `checkout_date`,(case when (`c`.`status` = '入住中') then '红' when (`r`.`status` = '空闲') then '绿' else '灰' end) AS `color_status` from (`room` `r` left join `checkin` `c` on(((`r`.`room_id` = `c`.`room_id`) and (`c`.`status` = '入住中'))));

-- ----------------------------
-- Triggers structure for table booking
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_delete_income_on_unpaid`;
delimiter ;;
CREATE TRIGGER `trg_delete_income_on_unpaid` AFTER UPDATE ON `booking` FOR EACH ROW BEGIN   IF NEW.payment_status <> 'paid' AND NEW.payment_status <> 'PAID' THEN     DELETE FROM income WHERE booking_id = NEW.id;   END IF; END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
