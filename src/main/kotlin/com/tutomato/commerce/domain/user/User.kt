package com.tutomato.commerce.domain.user

import jakarta.persistence.*


@Entity
class User(
    val name: String,
) {

    init {
        require(name.isNotBlank()) { "User name cannot be blank" }
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Embedded
    val balance: Balance = Balance()

}