package com.tutomato.commerce.domain.cart

import jakarta.persistence.NoResultException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import javax.management.InstanceAlreadyExistsException

@Service
@Transactional
class CartService (
    private val cartRepository: CartRepository
) {

    fun save(command: CartCommand.Save) : Cart {
        return cartRepository.save(command.toEntity())
    }

    fun save(command: CartCommand.SaveCartItem) : Cart {
        val cart = cartRepository.findByUserId(command.userId)
            ?: throw NoResultException("고유번호에 해당하는 장바구니가 존재하지않습니다.")

        cart.getItemByIds(command.productId, command.optionId)
            ?.let { throw InstanceAlreadyExistsException("이미 등록된 상품입니다.") }

        val cartItem = cartRepository.save(command.toEntity(cart))
        cart.addItem(cartItem)

        return cart;
    }

    fun findByUserId(userId: Long) : Cart {
        return cartRepository.findByUserId(userId)
            ?: throw NoResultException("사용자의 장바구니가 존재하지않습니다.")
    }

    fun updateItemAmount(command : CartCommand.UpdateItemAmount) {
        val cartItem = cartRepository.findItemById(command.cartItemId)
            ?: throw NoResultException("고유번호에 해당하는 장바구니 상품이 존재하지않습니다.")

        cartItem.updateQuantity(command.updateAmount)
        cartRepository.save(cartItem)
    }

    fun delete(command : CartCommand.Delete) {
        val cart = cartRepository.findById(command.cartId)
            ?: throw NoResultException("고유번호에 해당하는 장바구니가 존재하지않습니다.")

        cartRepository.delete(cart)
    }

    fun delete(command : CartCommand.DeleteCartItem) {
        val item = cartRepository.findItemById(command.cartItemId)
            ?: throw NoResultException("고유번호에 해당하는 장바구니 상품이 존재하지않습니다.")

        cartRepository.delete(item)
    }

}