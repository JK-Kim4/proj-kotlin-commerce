package com.tutomato.commerce.interfaces.cart

import com.tutomato.commerce.domain.authentication.Authenticated
import com.tutomato.commerce.domain.authentication.AuthenticatedUser
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
        @Authenticated authorization: AuthenticatedUser): ResponseEntity<CartResponse> {
        return ResponseEntity.ok(
            CartResponse.from(cartService.findByUserId(authorization.userId))
        )
    }

    @PostMapping("/item")
    override fun addItem(
        @Authenticated authorization: AuthenticatedUser,
        @RequestBody item: CartItemRequest
    ): ResponseEntity<CartResponse> {
        return ResponseEntity.ok(
            CartResponse.from(cartService.save(item.toCommand(authorization.userId)))
        )
    }

    @PutMapping("/items/{id}")
    override fun updateQuantity(
        @Authenticated authorization: AuthenticatedUser,
        @PathVariable(name = "id") id: Long,
        @RequestBody item: CartItemUpdateRequest
    ): ResponseEntity<Void> {
        cartService.updateItemAmount(item.toCommand(id))
        return ResponseEntity.ok().build()
    }
}