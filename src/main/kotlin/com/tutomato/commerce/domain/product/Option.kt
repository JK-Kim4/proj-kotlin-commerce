package com.tutomato.commerce.domain.product

import jakarta.persistence.*

@Entity
@Table(name = "product_option")
class Option (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product? = null,

    @Embedded
    val color: Color,

    @Enumerated(EnumType.STRING)
    val size: Size,

    @Embedded
    var stock: Stock = Stock(0),

) {

    fun hasEnoughStock(quantity: Int): Boolean {
        return stock.hasEnoughStock(quantity)
    }

    fun decreaseStock(amount: Int) {
        stock = stock.decrease(amount)
    }

    fun matches(target: Option): Boolean {
        return (target.color == this.color) && (target.size == this.size)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Option) return false

        return color == other.color && size == other.size
    }

    override fun hashCode(): Int {
        var result = color.hashCode()
        result = 31 * result + (size.hashCode())
        return result
    }

}