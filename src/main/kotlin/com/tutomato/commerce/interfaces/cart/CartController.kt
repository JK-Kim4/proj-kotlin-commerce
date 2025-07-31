package com.tutomato.commerce.interfaces.cart

import com.tutomato.commerce.domain.cart.CartService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/carts")
class CartController(
    private val cartService: CartService,
) : CartApiSpec{

    @GetMapping("/me")
    override fun cart(
        @RequestHeader("Authorization") authorization: Token
    ): ResponseEntity<CartResponse> {
        return ResponseEntity.ok(CartResponse(
            1L,
            listOf(
                CartItemResponse(1L, 1),
                CartItemResponse(2L, 4),
                CartItemResponse(3L, 5),
            ),
        ))
    }

    @PostMapping("/items")
    override fun addItem(
        @RequestHeader("Authorization") authorization: Token,
        @RequestBody items: CartItemsRequest
    ): ResponseEntity<CartResponse> {
        return ResponseEntity.ok(CartResponse(
            1L,
            items.items.stream()
                .map { item
                    -> CartItemResponse(item.productId, item.quantity) }
                .toList()
        ))
    }

    @PutMapping("/items/{id}")
    override fun updateQuantity(
        @RequestHeader("Authorization") authorization: Token,
        @PathVariable(name = "id") id: Long,
        @RequestBody items: CartItemUpdateRequest
    ): ResponseEntity<CartItemUpdateResponse> {
        TODO("Not yet implemented")
    }
}