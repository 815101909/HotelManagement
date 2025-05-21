-- 酒店管理系统数据库初始化脚本
-- 1. 管理员表
CREATE TABLE IF NOT EXISTS admin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. 员工表
CREATE TABLE IF NOT EXISTS employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    phone VARCHAR(20),
    position VARCHAR(50),
    hire_date DATE,
    salary DECIMAL(10,2),
    status TINYINT DEFAULT 1, -- 1在职 0离职
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 3. 房间表
CREATE TABLE IF NOT EXISTS room (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(20) NOT NULL UNIQUE,
    type VARCHAR(20),
    status VARCHAR(20), -- 空闲/已预订/维修等
    price DECIMAL(10,2),
    floor INT,
    description VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 4. 客户表
CREATE TABLE IF NOT EXISTS customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    phone VARCHAR(20),
    id_card VARCHAR(30),
    email VARCHAR(100),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 5. 预订表
CREATE TABLE IF NOT EXISTS booking (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    room_id INT NOT NULL,
    checkin_date DATE,
    checkout_date DATE,
    status VARCHAR(20), -- 已预订/已入住/已退房/已取消
    total_price DECIMAL(10,2),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (room_id) REFERENCES room(id)
);

-- 6. 财务表
CREATE TABLE IF NOT EXISTS finance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATE NOT NULL,
    revenue DECIMAL(12,2) DEFAULT 0,
    expense DECIMAL(12,2) DEFAULT 0,
    profit DECIMAL(12,2) DEFAULT 0,
    remark VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 7. 操作日志表
CREATE TABLE IF NOT EXISTS operation_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    admin_id INT,
    action VARCHAR(100),
    detail VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES admin(id)
);

-- 索引
CREATE INDEX idx_customer_phone ON customer(phone);
CREATE INDEX idx_room_status ON room(status);
CREATE INDEX idx_booking_status ON booking(status);

-- 插入测试数据
-- 管理员
INSERT INTO admin (username, password, name, phone, email) VALUES
('admin', '21232f297a57a5a743894a0e4a801fc3', '超级管理员', '13800000000', 'admin@hotel.com');

-- 员工
INSERT INTO employee (name, gender, phone, position, hire_date, salary, status) VALUES
('张三', '男', '13900000001', '前台', '2022-01-10', 4000.00, 1),
('李四', '女', '13900000002', '保洁', '2021-05-15', 3500.00, 1),
('王五', '男', '13900000003', '维修', '2020-09-20', 4200.00, 0);

-- 房间
INSERT INTO room (room_number, type, status, price, floor, description) VALUES
('101', '单人间', '空闲', 200.00, 1, '舒适单人间'),
('102', '双人间', '已预订', 300.00, 1, '宽敞双人间'),
('201', '套房', '空闲', 500.00, 2, '豪华套房');

-- 客户
INSERT INTO customer (name, gender, phone, id_card, email) VALUES
('赵六', '男', '13811112222', '110101199001010011', 'zhaoliu@example.com'),
('孙七', '女', '13833334444', '110101199202020022', 'sunqi@example.com');

-- 预订
INSERT INTO booking (customer_id, room_id, checkin_date, checkout_date, status, total_price) VALUES
(1, 2, '2023-07-01', '2023-07-03', '已预订', 600.00),
(2, 1, '2023-07-05', '2023-07-06', '已入住', 200.00);

-- 财务
INSERT INTO finance (date, revenue, expense, profit, remark) VALUES
('2023-07-01', 800.00, 200.00, 600.00, '月初结算'),
('2023-07-02', 1000.00, 300.00, 700.00, '日常收入');

-- 操作日志
INSERT INTO operation_log (admin_id, action, detail) VALUES
(1, '登录', '管理员登录系统'),
(1, '添加房间', '添加房间101'); 