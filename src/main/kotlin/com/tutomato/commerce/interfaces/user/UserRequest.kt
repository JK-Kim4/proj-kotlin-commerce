package com.tutomato.commerce.interfaces.user

class UserRequest {

    class Token (val token: String)

    class Save (val email: String, val password: String)

}