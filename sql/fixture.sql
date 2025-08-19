-- 안전 모드 해제
SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS = 0;

-- 기존 프로시저 삭제
DROP PROCEDURE IF EXISTS generate_orders;
DROP PROCEDURE IF EXISTS generate_order_lines;
DROP PROCEDURE IF EXISTS generate_payments;
DROP PROCEDURE IF EXISTS generate_products;

-- 데이터 정리 (자식 테이블부터)
TRUNCATE TABLE payment;
TRUNCATE TABLE order_line;
TRUNCATE TABLE orders;
TRUNCATE TABLE product_option;
TRUNCATE TABLE product;
TRUNCATE TABLE user;

-- ------------------------------
-- 상품 데이터 생성 프로시저 (10만 개 배치)
-- ------------------------------
DELIMITER $$
CREATE PROCEDURE generate_products(IN start_id BIGINT, IN batch_size INT)
BEGIN
  DECLARE i INT DEFAULT 0;

  WHILE i < batch_size DO
    INSERT INTO product (
      id, name, description, published_date, sale_status, category, price, created_at, updated_at
    )
    VALUES (
      start_id + i,
      CONCAT('Product_', start_id + i),
      CONCAT('Description for product ', start_id + i),
      DATE_SUB(CURDATE(), INTERVAL FLOOR(RAND() * 365) DAY),
      ELT(FLOOR(1 + RAND() * 3), 'ON_SALE', 'OUT_OF_STOCK', 'DISCONTINUED'),
      ELT(FLOOR(1 + RAND() * 5), 'TOP', 'BOTTOM', 'SHOES', 'UNDERWEAR', 'ACCESSORY'),
      FLOOR(1000 + RAND() * 99000),
      NOW(), NOW()
    );
    SET i = i + 1;
END WHILE;
END;
$$
DELIMITER ;

-- ------------------------------
-- 주문 데이터 생성 프로시저
-- ------------------------------
DELIMITER $$
CREATE PROCEDURE generate_orders(IN total INT)
BEGIN
  DECLARE i INT DEFAULT 1;
  WHILE i <= total DO
    INSERT INTO orders (
      order_id, user_id, order_status, sub_total_amount, created_at, updated_at, paid_at
    )
    VALUES (
      i,
      FLOOR(1 + RAND() * 10000),
      ELT(FLOOR(1 + RAND() * 5), 'PAID', 'CREATED', 'PENDING', 'EXPIRED', 'CANCELED'),
      FLOOR(10000 + RAND() * 490000),
      NOW(), NOW(),
      IF(RAND() < 0.5, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 365) DAY), NULL)
    );
    SET i = i + 1;
END WHILE;
END;
$$
DELIMITER ;

-- ------------------------------
-- 주문 상품 데이터 생성 프로시저
-- ------------------------------
DELIMITER $$
CREATE PROCEDURE generate_order_lines(IN total INT)
BEGIN
  DECLARE i INT DEFAULT 1;

  WHILE i <= total DO
    INSERT INTO order_line (
      order_line_id, order_id, product_id, option_id, quantity, price
    )
    VALUES (
      i,
      FLOOR(1 + RAND() * 10000),
      FLOOR(1 + RAND() * 100000),
      FLOOR(1 + RAND() * 100000),
      FLOOR(1 + RAND() * 5),
      FLOOR(1000 + RAND() * 49000)
    );
    SET i = i + 1;
END WHILE;
END;
$$
DELIMITER ;

-- ------------------------------
-- 결제 데이터 생성 프로시저
-- ------------------------------
DELIMITER $$
CREATE PROCEDURE generate_payments(IN total INT)
BEGIN
  DECLARE i INT DEFAULT 1;

  WHILE i <= total DO
    INSERT INTO payment (
      payment_id, order_id, user_id, amount, method, type, paid_at, created_at, updated_at
    )
    VALUES (
      i,
      i,
      FLOOR(1 + RAND() * 10000),
      FLOOR(10000 + RAND() * 490000),
      ELT(FLOOR(1 + RAND() * 2), 'POINT', 'CARD'),
      ELT(FLOOR(1 + RAND() * 2), 'PAY', 'REFUND'),
      IF(RAND() < 0.8, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 365) DAY), NULL),
      NOW(), NOW()
    );
    SET i = i + 1;
END WHILE;
END;
$$
DELIMITER ;

-- ------------------------------
-- 실행
-- ------------------------------
CALL generate_products(1, 100000);
CALL generate_orders(10000);
CALL generate_order_lines(20000);
CALL generate_payments(10000);

-- ------------------------------
-- 프로시저 제거
-- ------------------------------
DROP PROCEDURE IF EXISTS generate_orders;
DROP PROCEDURE IF EXISTS generate_order_lines;
DROP PROCEDURE IF EXISTS generate_payments;
DROP PROCEDURE IF EXISTS generate_products;

-- FK 복원
SET FOREIGN_KEY_CHECKS = 1;
SET SQL_SAFE_UPDATES = 1;
