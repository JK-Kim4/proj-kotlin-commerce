package com.tutomato.commerce.support.domain

import com.tutomato.commerce.domain.product.*

class OptionDomainSupport {

    companion object {
        fun 기본_옵션_생성() : Option{
            return Option(
                color = Color("#ffffff"),
                size = Size.XS,
                stock = Stock(10)
            )
        }

        fun 옵션_생성_고유번호(id: Long) : Option {
            val option = 기본_옵션_생성()
            option.id = id
            return option
        }

        fun 옵션_생성_STOCK(stock: Int) : Option{
            return Option(
                color = Color("#ffffff"),
                size = Size.XS,
                stock = Stock(stock)
            )
        }

        fun 옵션_생성_COLOR_SIZE(color: Color, size: Size) : Option {
            return Option(
                color = color,
                size = size,
                )
        }

        fun 옵션_생성_COLOR_SIZE_PRODUCTID(color: Color, size: Size, product: Product) : Option {
            var option = Option(
                color = color,
                size = size,
            )
            option.product = product

            return option
        }
    }

}