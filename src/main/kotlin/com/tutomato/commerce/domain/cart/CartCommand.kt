package com.tutomato.commerce.domain.cart

class CartCommand {

    data class Save(
        val userId: Long,
    ) {
        fun toEntity() : Cart {
            return Cart(userId = userId)
        }
    }

    data class SaveCartItem(
        val userId: Long,
        val productId: Long,
        val optionId: Long,
        val quantity: Int,
    ) {
        fun toEntity(cart: Cart) : CartItem {
            return CartItem(
                productId = productId,
                optionId = optionId,
                quantity = quantity,
                cart = cart
            )
        }
    }

    data class UpdateItemAmount(
        val cartItemId: Long,
        val updateAmount: Int,
    )

    data class Delete(
        val cartId : Long,
    )

    data class DeleteCartItem(
        val cartItemId : Long,
    )
}