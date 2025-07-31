package com.tutomato.commerce.domain.authentication

class AuthenticatedUser(
    val userId: Long,
    val userName: String,
    val roles: List<Role>
) {

    constructor(userId: Long, userName: String, rolesString: String) : this(
        userId,
        userName,
        rolesString.split(",").map { Role.valueOf(it.trim()) }
    )

}