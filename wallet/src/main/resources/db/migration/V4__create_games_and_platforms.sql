-- 创建游戏表
CREATE TABLE `games` (
  `game_id` int NOT NULL AUTO_INCREMENT COMMENT '游戏的唯一标识符，自增主键',
  `user_id` bigint NOT NULL COMMENT '用户ID，关联到 users 表的 user_id',
  `game_name` varchar(100) NOT NULL COMMENT '游戏名称（如 "APEX"、"LOL"、"王者荣耀"）',
  `game_code` varchar(50) NOT NULL COMMENT '游戏代码（如 "APEX" 代表 APEX Legends）',
  `description` text COMMENT '游戏描述信息，可选',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`game_id`),
  UNIQUE KEY `user_game_code` (`user_id`,`game_code`) COMMENT '同一用户的游戏代码必须唯一',
  CONSTRAINT `games_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='存储用户自定义游戏信息的表';

-- 创建平台表
CREATE TABLE `platforms` (
  `platform_id` tinyint NOT NULL AUTO_INCREMENT COMMENT '平台的唯一标识符，自增主键',
  `user_id` bigint NOT NULL COMMENT '用户ID，关联到 users 表的 user_id',
  `platform_name` varchar(50) NOT NULL COMMENT '平台名称（如 "淘宝"、"京东"）',
  `platform_code` varchar(10) NOT NULL COMMENT '平台代码（如 "TB" 代表淘宝）',
  `description` text COMMENT '平台描述信息，可选',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`platform_id`),
  UNIQUE KEY `user_platform_code` (`user_id`,`platform_code`) COMMENT '同一用户的平台代码必须唯一',
  CONSTRAINT `platforms_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='存储用户自定义平台信息的表'; 