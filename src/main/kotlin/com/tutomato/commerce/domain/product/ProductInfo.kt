package com.tutomato.commerce.domain.product

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDate

@Embeddable
class ProductInfo(

    val name: String,
    val description: String,

    @Column(name = "published_date")
    val publishedDate: LocalDate,
    //val seller: Seller,

    ) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductInfo

        if (name != other.name) return false
        if (description != other.description) return false
        if (publishedDate != other.publishedDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + publishedDate.hashCode()
        return result
    }
}
