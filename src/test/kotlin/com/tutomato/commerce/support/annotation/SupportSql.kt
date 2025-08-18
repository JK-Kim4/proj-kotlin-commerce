package com.tutomato.commerce.support.annotation

import org.springframework.test.context.jdbc.Sql

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Sql(
    scripts = [
        "classpath:data/product.sql",
        "classpath:data/user.sql",
        "classpath:data/order_payment.sql",
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
annotation class SupportSql
