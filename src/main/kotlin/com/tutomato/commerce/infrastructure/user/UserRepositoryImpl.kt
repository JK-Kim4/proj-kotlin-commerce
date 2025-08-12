package com.tutomato.commerce.infrastructure.user

import com.tutomato.commerce.domain.user.User
import com.tutomato.commerce.domain.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
): UserRepository {

    override fun save(user: User): User {
        return userJpaRepository.save(user)
    }

    override fun findById(userId: Long): User? {
        return userJpaRepository.findById(userId).orElse(null)
    }

    override fun flush() {
        userJpaRepository.flush()
    }
}