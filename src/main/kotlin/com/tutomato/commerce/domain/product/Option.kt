package com.tutomato.commerce.domain.product

import jakarta.persistence.*

@Entity
@Table(name = "product_option")
class Option (

    @Embedded
    val color: Color?,

    @Enumerated(EnumType.STRING)
    val size: Size?,

    @Embedded
    val stock: Stock? = Stock(0),

) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "product_id")
    var productId: Long? = null

    fun matches(target: Option): Boolean {
        return (target.color == null || target.color == this.color) &&
                (target.size == null || target.size == this.size)
    }

    fun isEqualColor(color: Color) : Boolean {
        return this.color == color
    }

    fun isEqualSize(size: Size) : Boolean {
        return this.size == size
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Option) return false

        return color == other.color && size == other.size
    }

    override fun hashCode(): Int {
        var result = color?.hashCode() ?: 0
        result = 31 * result + (size?.hashCode() ?: 0)
        return result
    }

}