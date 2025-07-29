package com.tutomato.commerce.domain.product

import jakarta.persistence.CascadeType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

class Options(

    @OneToMany(cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @JoinColumn(name = "product_id")
    val options: List<Option>,

) {

    constructor() : this(emptyList())

    fun setProductId(productId: Long) {
        options.forEach { it.productId = productId }
    }

    fun size() : Int {
        return options.size
    }

    fun hasMatchesOption(option: Option) : Boolean {
        return options.any { it.matches(option) }
    }

    fun hasEqualOption(option: Option) : Boolean {
        return options.contains(option)
    }

    fun getStockByOption(option: Option): Stock? {
        return options.first { it.matches(option) }.stock
    }

    fun add(option: Option): Options {
        if (hasEqualOption(option)) {
            throw IllegalArgumentException("Option already exists")
        }

        val newList = options + option // 기존 리스트에 새 옵션 추가
        return Options(newList)
    }

    fun remove(option: Option): Options {
        val newList = options - option
        return Options(newList)
    }

    fun remove(optionId: Long): Options {
        val newList = options.filter { it.id != optionId }
        return Options(newList)
    }

    fun optionAt(index: Int): Option {
        return options[index]
    }

    fun optionById(id: Long): Option {
        return options.first { it.id == id }
    }


}
