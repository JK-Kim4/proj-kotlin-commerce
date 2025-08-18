package com.tutomato.commerce.domain.user

interface UserRepository {

    fun save(user: User): User

    fun findById(userId: Long): User?

    fun flush()

}