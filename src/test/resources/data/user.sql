-- 테이블 데이터 초기화
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE user;
SET FOREIGN_KEY_CHECKS = 1;


-- 사용자 데이터 삽입
INSERT INTO USER (id, name, balance, version)
VALUES
    (1, "test user1", 100000, 3),
    (2, "test user2", 200000, 0),
    (3, "test user3", 300000, 1),
    (4, "test user4", 400000, 3),
    (5, "test user5", 500000, 2);