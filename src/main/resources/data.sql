-- 初始化测试用户（密码是 123456，已用 BCrypt 加密）
-- 使用 MySQL 的 IGNORE 语法，如果条目已存在则忽略，避免重复插入导致无法启动，下同
INSERT IGNORE INTO users (username, password, full_name)
VALUES ('testuser', '$2a$10$UEwMZTMQo1wVipiTsPrCFevVjblJUmlZd5Ud69utZEokMIi/IcWxu', '测试用户');

-- 初始化商品数据
-- 使用 INSERT IGNORE 配合手动指定 ID 来防止重复商品条目
INSERT IGNORE INTO products (id, name, description, price, image_url)
VALUES
    (1, '小米手机', '高性能智能手机，8GB+256GB', 2999.00, 'https://picsum.photos/200/200?random=1'),
    (2, '华为平板', '10.4英寸大屏，学习办公神器', 1899.00, 'https://picsum.photos/200/200?random=2'),
    (3, '苹果耳机', '无线蓝牙耳机，降噪功能', 1299.00, 'https://picsum.photos/200/200?random=3'),
    (4, '联想笔记本', '轻薄本，16GB+512GB', 4999.00, 'https://picsum.photos/200/200?random=4'),
    (5, '大疆无人机', '高清航拍，便携折叠', 3699.00, 'https://picsum.photos/200/200?random=5');