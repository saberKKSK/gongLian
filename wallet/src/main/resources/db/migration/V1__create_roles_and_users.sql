-- 创建角色表
CREATE TABLE IF NOT EXISTS roles (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建用户表（如果还没有的话）
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    alipay VARCHAR(100),
    wechat VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_role FOREIGN KEY (role) REFERENCES roles(role_name)
);

-- 插入基础角色数据
INSERT INTO roles (role_name, description) VALUES
('ADMIN', '系统管理员'),
('MERCHANT', '商家用户'),
('DRIVER', '司机用户');

-- 插入测试用户（密码为 admin123）
INSERT INTO users (username, password_hash, role) VALUES
('admin', '$2a$10$rDmFN6ZqYNrqKLJXIQq9L.PeM5JUriRRZpRW7L4nGqmqaHRqr4/Hy', 'ADMIN'),
('merchant1', '$2a$10$rDmFN6ZqYNrqKLJXIQq9L.PeM5JUriRRZpRW7L4nGqmqaHRqr4/Hy', 'MERCHANT'),
('driver1', '$2a$10$rDmFN6ZqYNrqKLJXIQq9L.PeM5JUriRRZpRW7L4nGqmqaHRqr4/Hy', 'DRIVER'); 