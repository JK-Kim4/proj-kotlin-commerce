package com.tutomato.commerce.domain.cart

class CartCommand {

    class Save(
        val userId: Long,
    ) {
        fun toEntity() : Cart {
            return Cart(userId = userId)
        }
    }

    class SaveCartItem(
        val cartId: Long,
        val productId: Long,
        val optionId: Long,
        val amount: Int,
    ) {
        fun toEntity(cart: Cart) : CartItem {
            return CartItem(
                productId = productId,
                optionId = optionId,
                amount = amount,
                cart = cart
            )
        }
    }

    class UpdateItemAmount(
        val cartItemId: Long,
        val updateAmount: Int,
    )

    class Delete(
        val cartId : Long,
    )

    class DeleteCartItem(
        val cartItemId : Long,
    )
}