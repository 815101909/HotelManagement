-- 8. 示例数据

-- 管理员
INSERT INTO admin (username, password, name, phone, email, last_login) VALUES
('admin', 'admin123', '系统管理员', '13800000000', 'admin@hotel.com', NOW());

-- 员工
INSERT INTO employee (name, gender, phone, position, hire_date, status) VALUES
('张三', '男', '13900000001', '前台', '2023-01-10', '在职'),
('李四', '女', '13900000002', '保洁', '2023-02-15', '在职');

-- 房型
INSERT INTO room_type (type_name, description, price) VALUES
('单人间', '一张单人床，适合1人入住', 200.00),
('标准间', '两张单人床，适合2人入住', 300.00),
('大床房', '一张大床，适合情侣或夫妻', 350.00);

-- 房间
INSERT INTO room (room_number, type_id, floor, status) VALUES
('101', 1, 1, '空闲'),
('102', 2, 1, '占用'),
('201', 3, 2, '空闲');

-- 入住登记
INSERT INTO checkin (room_id, guest_name, guest_phone, checkin_date, checkout_date, status) VALUES
(2, '王五', '13900000003', '2024-06-01 14:00:00', NULL, '入住中');

-- 营收
INSERT INTO revenue (date, total_income, note) VALUES
('2024-06-01', 350.00, '大床房入住1间'); 