CREATE DATABASE IF NOT EXISTS used_car_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE used_car_db;

DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    enabled TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cars (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    mileage INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    color VARCHAR(50),
    fuel_type VARCHAR(50),
    description TEXT,
    image_url VARCHAR(500),
    status VARCHAR(30) NOT NULL DEFAULT 'ON_SALE',
    category_id BIGINT,
    owner_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_car_category FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT fk_car_owner FOREIGN KEY (owner_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE messages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    car_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    reply TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    contact_phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_message_car FOREIGN KEY (car_id) REFERENCES cars(id),
    CONSTRAINT fk_message_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO users (username, password, nickname, phone, email, role) VALUES
('admin', '$2a$10$6m0owW9mObJxLvR3AiZr8eZxjW5Cj6VsXl8G5G3VtX0bCX5BODW9a', '管理员', '13800000000', 'admin@usedcar.com', 'ADMIN'),
('demo', '$2a$10$6m0owW9mObJxLvR3AiZr8eZxjW5Cj6VsXl8G5G3VtX0bCX5BODW9a', '张三', '13900000000', 'demo@usedcar.com', 'USER');

INSERT INTO categories (name, parent_id, sort_order) VALUES
('轿车', NULL, 1),
('SUV', NULL, 2),
('MPV', NULL, 3),
('皮卡', NULL, 4),
('新能源', NULL, 5);

INSERT INTO cars (title, brand, model, year, mileage, price, color, fuel_type, description, image_url, status, category_id, owner_id) VALUES
('2020款大众速腾 1.4T 自动舒适版', '大众', '速腾', 2020, 42000, 129800.00, '白色', '汽油', '车况良好，保养及时，双气囊，倒车影像。', 'https://images.unsplash.com/photo-1552519507-da3b142c6e3d?auto=format&fit=crop&w=800&q=80', 'ON_SALE', 1, 2),
('2019款本田CR-V 2.0L 自动舒适版', '本田', 'CR-V', 2019, 51000, 158000.00, '黑色', '汽油', '城市SUV，行驶里程适中，内饰整洁。', 'https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?auto=format&fit=crop&w=800&q=80', 'ON_SALE', 2, 2),
('2021款比亚迪宋PLUS EV', '比亚迪', '宋PLUS EV', 2021, 18000, 168800.00, '蓝色', '电动', '电池健康度良好，适合城市通勤。', 'https://images.unsplash.com/photo-1503376780353-7e6692767b70?auto=format&fit=crop&w=800&q=80', 'ON_SALE', 5, 2),
('2018款丰田威驰 1.5L 自动锐放版', '丰田', '威驰', 2018, 63000, 78900.00, '银灰色', '汽油', '车辆手续齐全，适合家庭代步车。', 'https://images.unsplash.com/photo-1544636331-e26879cd4d9b?auto=format&fit=crop&w=800&q=80', 'ON_SALE', 1, 2);

INSERT INTO messages (car_id, user_id, content, reply, status, contact_phone) VALUES
(1, 2, '这辆车是否有过事故？能否看车？', '没有重大事故，欢迎预约看车。', 'REPLIED', '13900000000'),
(2, 2, '车贷是否可以办理？', '可以协助办理相关手续。', 'REPLIED', '13900000000');

SELECT '数据库初始化完成' AS result;
