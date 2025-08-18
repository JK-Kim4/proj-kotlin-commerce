-- 테이블 데이터 초기화
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE product;
TRUNCATE TABLE product_option;
SET FOREIGN_KEY_CHECKS = 1;


-- 상품 데이터 삽입
INSERT INTO product (id, name, description, published_date, sale_status, category, price, created_at, updated_at)
VALUES
    (1, "test product1", "test product1 desc", "2025-01-01", "ON_SALE", "TOP", 50000, now(), now()),
    (2, "test product2", "test product2 desc", "2025-01-01", "ON_SALE", "BOTTOM", 30000, now(), now()),
    (3, "test product3", "test product3 desc", "2025-01-01", "ON_SALE", "UNDERWEAR", 10000, now(), now()),
    (4, "test product4", "test product4 desc", "2025-01-01", "ON_SALE", "SHOES", 20000, now(), now()),
    (5, "test product5", "test product5 desc", "2025-01-01", "ON_SALE", "ACCESSORY", 5000, now(), now());


-- 옵션 데이터 삽입 (product_id 논리적 FK)
INSERT INTO product_option (id, product_id, color_code, size, stock)
VALUES
    (1, 1, "#ffffff", "M", 50),
    (2, 1, "#ffffff", "S", 50),
    (3, 1, "#ffffff", "L", 10),
    (4, 2, "#dfaerd", "M", 12),
    (5, 3, "#fjdjvd", "M", 12),
    (6, 4, "#112345", "L", 12),
    (7, 5, "#440234", "XL", 1);
