-- ----------------------------------------------------
-- 더미 데이터 생성을 위한 프로시저 정의 스크립트
-- 순서: Product → ProductOption → Order → OrderLine → Payment
-- ----------------------------------------------------

DELIMITER $$

-- 1. 상품 생성 프로시저
CREATE PROCEDURE populate_products(IN product_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;

    WHILE i < product_count DO
        INSERT INTO `product` (
            `name`, `description`, `published_date`, `sale_status`, `category`,
            `price`, `created_at`, `updated_at`
        ) VALUES (
            CONCAT('상품명_', i + 1),
            CONCAT('설명_', i + 1),
            DATE_SUB(CURDATE(), INTERVAL (i % 365) DAY),
            'ON_SALE',
            ELT((i % 7) + 1, 'TOP', 'BOTTOM', 'OUTER', 'UNDERWEAR', 'SHOES', 'ACCESSORY', 'ETC'),
            10000 + (i % 90) * 1000,
            NOW(), NOW()
        );
        SET i = i + 1;
END WHILE;
END;
$$

-- 2. 상품 옵션 생성 프로시저
CREATE PROCEDURE populate_product_options()
BEGIN
INSERT INTO `product_option` (`product_id`, `color_code`, `size`, `stock`)
SELECT
    p.`id`,
    cm.`color_code`,
    ELT(seq.`n` + 1, 'S', 'M', 'L', 'XL'),
    0
FROM `product` p
         JOIN (
    SELECT 0 AS `n` UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3
) seq
         JOIN (
    SELECT 0 AS `idx`, '#000000' AS `color_code` UNION ALL
    SELECT 1, '#FF0000' UNION ALL
    SELECT 2, '#00AA00' UNION ALL
    SELECT 3, '#0000FF' UNION ALL
    SELECT 4, '#FFFFFF' UNION ALL
    SELECT 5, '#808080'
) cm ON cm.`idx` = (p.`id` + seq.`n`) % 6
  LEFT JOIN `product_option` po
ON po.`product_id` = p.`id`
    AND po.`color_code` = cm.`color_code`
    AND po.`size` = ELT(seq.`n` + 1, 'S', 'M', 'L', 'XL')
WHERE po.`product_id` IS NULL;
END;
$$

-- 3. 주문 생성 프로시저
CREATE PROCEDURE populate_orders(IN order_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE rand_status VARCHAR(50);

    WHILE i < order_count DO
        -- 상태를 확률 기반으로 선택: PAID(60%) / CREATED / CANCELED / PENDING / EXPIRED
        SET rand_status = ELT(
            FLOOR(1 + RAND() * 10),  -- 1 ~ 10
            'PAID', 'PAID', 'PAID', 'PAID', 'PAID', 'PAID',  -- 60%
            'CREATED', 'CANCELED', 'PENDING', 'EXPIRED'      -- 40%
        );

INSERT INTO `orders` (
    `user_id`,
    `order_status`,
    `sub_total_amount`,
    `paid_at`,
    `created_at`,
    `updated_at`
)
VALUES (
           1 + FLOOR(RAND() * 30000),  -- user_id: 1 ~ 30000 랜덤
           rand_status,
           10000 + FLOOR(RAND() * 90000),  -- 10,000 ~ 99,999
           CASE
               WHEN rand_status = 'PAID' THEN
               TIMESTAMP(DATE('2025-01-01') + INTERVAL FLOOR(RAND() * DATEDIFF(CURDATE(), '2025-01-01')) DAY)
               ELSE NULL
               END,
           NOW(),
           NOW()
       );

SET i = i + 1;
END WHILE;
END;
$$

-- 4. 주문 라인 생성 프로시저
CREATE PROCEDURE populate_order_lines_fixed()
BEGIN
    DECLARE total_order_lines INT DEFAULT 150000;

    -- ① 랜덤 product_option 2,000개를 미리 샘플링
    CREATE TEMPORARY TABLE sampled_product_option AS
SELECT id, product_id
FROM product_option
ORDER BY RAND()
    LIMIT 2000;

-- ② orders × 3 (시퀀스 0~2) 생성 후 CROSS JOIN with 옵션
INSERT INTO order_line (
    order_id,
    product_id,
    option_id,
    price,
    quantity
)
SELECT
    o.order_id,
    po.product_id,
    po.id,
    (10000 + (po.id % 50) * 100) * qty.q,
    qty.q
FROM (
         SELECT o1.order_id
         FROM orders o1
                  JOIN (
             SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2
         ) seq ON 1=1
             LIMIT total_order_lines
     ) o
         JOIN sampled_product_option po ON 1=1
         JOIN (
    SELECT 1 AS q UNION ALL SELECT 2 UNION ALL SELECT 3
) qty ON qty.q = FLOOR(1 + RAND() * 3)
ORDER BY RAND()
    LIMIT total_order_lines;

-- ③ 임시 테이블 정리
DROP TEMPORARY TABLE sampled_product_option;
END;
$$




-- 5. 결제 생성 프로시저
CREATE PROCEDURE populate_payments()
BEGIN
INSERT INTO `payment` (
    `order_id`, `user_id`, `amount`,
    `method`, `type`, `paid_at`,
    `created_at`, `updated_at`
)
SELECT
    o.`order_id`,
    o.`user_id`,
    o.`sub_total_amount`,
    'POINT', 'PAY',
    o.`paid_at`,
    NOW(), NOW()
FROM `orders` o
         LEFT JOIN `payment` p ON p.`order_id` = o.`order_id`
WHERE o.`order_status` = 'PAID'
  AND p.`order_id` IS NULL;
END;
$$

DELIMITER ;


DROP PROCEDURE IF EXISTS populate_products;
DROP PROCEDURE IF EXISTS populate_product_options;
DROP PROCEDURE IF EXISTS populate_orders;
DROP PROCEDURE IF EXISTS populate_order_lines_fixed;
DROP PROCEDURE IF EXISTS populate_payments;
-- 🔄 샘플 실행 순서 (원하면 주석 해제)
CALL populate_products(100000);
CALL populate_product_options();
CALL populate_orders(50000);
CALL populate_order_lines_fixed();
CALL populate_payments();

select count(*) from product;
select count(*) from product_option;
select count(*) from orders;
select count(*) from order_line;
truncate order_line;


