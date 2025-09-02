SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE product;
TRUNCATE TABLE product_option;
TRUNCATE TABLE orders;
TRUNCATE TABLE order_line;
SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE orders MODIFY COLUMN paid_at datetime(6) NULL;



-- 상품 등록
INSERT INTO product (id, name, description, published_date, sale_status, category, price, created_at, updated_at)
VALUES
    (1, "월간 판매량 1위 상품 - 10개", "test product1 desc", "2025-01-01", "ON_SALE", "TOP", 50000, now(), now()),
    (2, "월간 판매량 2위 상품 - 8개", "test product2 desc", "2025-01-01", "ON_SALE", "BOTTOM", 30000, now(), now()),
    (3, "월간 판매량 3위 상품 - 6개", "test product3 desc", "2025-01-01", "ON_SALE", "UNDERWEAR", 10000, now(), now()),
    (4, "월간 판매량 4위 상품 - 4개", "test product4 desc", "2025-01-01", "ON_SALE", "SHOES", 20000, now(), now()),
    (5, "월간 판매량 5위 상품 - 2개", "test product5 desc", "2025-01-01", "ON_SALE", "ACCESSORY", 5000, now(), now()),
    (6, "판매량 미집계 상품 - 과거 판매 이력", "test product6 desc", "2020-01-01", "ON_SALE", "ACCESSORY", 5000, now(), now());


-- 상품 옵션 등록
INSERT INTO product_option (id, product_id, color_code, size, stock)
VALUES
    (1, 1, "#ffffff", "M", 50),
    (2, 1, "#ffffff", "S", 50),
    (3, 2, "#ffffff", "L", 50),
    (4, 2, "#dfaerd", "M", 50),
    (5, 3, "#fjdjvd", "M", 50),
    (6, 3, "#112345", "L", 50),
    (7, 4, "#440234", "XL", 50),
    (8, 4, "#440234", "XL", 50),
    (9, 5, "#440234", "XL", 50),
    (10, 5, "#440234", "XL", 50),
    (101, 6, "#440234", "XL", 50);


-- 주문 등록
INSERT INTO orders (order_id, user_id, order_status, sub_total_amount, created_at, updated_at, paid_at)
VALUES
    (1, 1, "PAID", 500000, now(), now(), "2025-01-01 00:00:00"),
    (2, 2, "PAID", 500000, now(), now(), "2024-12-2 00:00:00"),
    (3, 3, "PAID", 300000, now(), now(), "2024-12-10 00:00:00"),
    (4, 4, "PAID", 400000, now(), now(), "2024-12-12 00:00:00"),
    (5, 4, "PAID", 400000, now(), now(), "2024-12-31 00:00:00"),
    (6, 5, "PAID", 500000, now(), now(), "2024-12-25 00:00:00"),
    (7, 1, "PAID", 100000, now(), now(), "2024-12-2 00:00:00"),
    (8, 2, "PAID", 200000, now(), now(), "2024-12-23 00:00:00"),
    (9, 2, "PAID", 200000, now(), now(), "2024-12-14 00:00:00"),
    (10, 2, "PAID", 200000, now(), now(), "2024-11-30 00:00:00"), -- 기간 검색 제외 주문
    (11, 2, "CANCELED", 200000, now(), now(), null),
    (12, 2, "EXPIRED", 200000, now(), now(), null),
    (13, 99, "PAID", 200000, now(), now(), "2023-11-30 00:00:00");


-- 주문 상품 등록
INSERT INTO order_line (order_line_id, order_id, product_id, option_id, quantity, price)
VALUES
    (1, 1, 1, 1, 3, 150000), -- 3개 * 50,000
    (2, 2, 1, 2, 2, 100000),
    (3, 3, 1, 1, 2, 100000),
    (4, 4, 1, 2, 3, 150000), -- 총합: 10개

    (5, 5, 2, 3, 3, 90000),
    (6, 6, 2, 4, 2, 60000),
    (7, 7, 2, 4, 3, 90000),  -- 총합: 8개

    (8, 8, 3, 5, 4, 40000),
    (9, 9, 3, 6, 2, 20000),  -- 총합: 6개

    (10, 1, 4, 7, 2, 40000),
    (11, 2, 4, 8, 2, 40000), -- 총합: 4개

    (12, 3, 5, 9, 1, 5000),
    (13, 4, 5, 10, 1, 5000),  -- 총합: 2개

    (14, 13, 6, 10, 50, 5000); -- 총합: 50개 (미집계)
