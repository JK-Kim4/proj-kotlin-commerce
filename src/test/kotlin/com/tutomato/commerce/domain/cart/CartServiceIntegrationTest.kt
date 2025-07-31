package com.tutomato.commerce.domain.cart

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers


@SpringBootTest
@Testcontainers
class CartServiceIntegrationTest(
    @Autowired private val cartService: CartService,
    @Autowired private val cartRepository: CartRepository,
) {


}