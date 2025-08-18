package com.tutomato.commerce.domain.user

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.NoResultException
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun findById(id: Long): User {
        return userRepository.findById(id)
            ?: throw NoResultException("User with id $id was found")
    }

    fun deductPoint(command: UserCommand.DeductPoint) {
        val user = userRepository.findById(command.userId)
            ?: throw NoResultException("User with id $id was found")

        user.pay(Money(command.amount))
    }

}