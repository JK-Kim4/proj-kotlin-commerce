-- 테이블 데이터 초기화
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE orders;
TRUNCATE TABLE order_line;
SET FOREIGN_KEY_CHECKS = 1;


-- 주문 데이터 삽입
INSERT INTO orders (order_id, user_id, order_status, sub_total_amount, created_at, updated_at)
VALUES
    (1, 1, "PENDING", 100000, now(), now()),
    (2, 2, "PENDING", 200000, now(), now()),
    (3, 3, "CREATED", 300000, now(), now()),
    (4, 4, "PAID", 400000, now(), now()),
    (5, 4, "EXPIRED", 400000, now(), now()),
    (6, 5, "CANCELED", 500000, now(), now());


-- 주문 상품 데이터 삽입 (order_id 논리적 FK)
INSERT INTO order_line (order_line_id, order_id, product_id, option_id, quantity, price)
VALUES
    (1, 1, 1, 1, 1, 50000),
    (2, 1, 1, 2, 2, 50000),
    (3, 2, 3, 5, 1, 10000),
    (4, 3, 4, 6, 1, 20000),
    (5, 4, 5, 7, 1, 5000),
    (6, 5, 1, 2, 1, 50000),
    (7, 6, 2, 3, 1, 30000);