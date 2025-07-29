package com.tutomato.commerce.domain.product

import jakarta.persistence.Embeddable

@Embeddable
class Color(

    val code: String,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Color) return false
        return code == other.code
    }

    override fun hashCode(): Int = code.hashCode()

}