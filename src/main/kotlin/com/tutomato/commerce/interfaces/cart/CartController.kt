package com.tutomato.commerce.interfaces.cart

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/carts")
class CartController : CartApiSpec{

    @GetMapping("/me")
    override fun cart(
        @RequestHeader("Authorization") authorization: CartRequest.Token
    ): ResponseEntity<CartResponse.Cart> {
        return ResponseEntity.ok(CartResponse.Cart(
            1L,
            listOf(
                CartResponse.Item(1L, 1),
                CartResponse.Item(2L, 4),
                CartResponse.Item(3L, 5),
            ),
        ))
    }

    @PostMapping("/items")
    override fun addCart(
        @RequestHeader("Authorization") authorization: CartRequest.Token,
        @RequestBody items: CartRequest.Items
    ): ResponseEntity<CartResponse.Cart> {
        return ResponseEntity.ok(CartResponse.Cart(
            1L,
            items.items.stream()
                .map { item
                    -> CartResponse.Item(item.productId, item.quantity) }
                .toList()
        ))
    }
}