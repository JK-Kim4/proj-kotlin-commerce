package com.tutomato.commerce.infrastructure.user

import com.tutomato.commerce.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository: JpaRepository<User, Long> {
}