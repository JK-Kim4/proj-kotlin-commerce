package com.tutomato.commerce.support.domain

import com.tutomato.commerce.domain.product.Color
import com.tutomato.commerce.domain.product.Option
import com.tutomato.commerce.domain.product.Size
import com.tutomato.commerce.domain.product.Stock

class OptionDomainSupport {

    companion object {
        fun 기본_옵션_생성() : Option{
            return Option(Color("#ffffff"), Size.XS, Stock(10))
        }

        fun 옵션_생성_COLOR_SIZE(color: Color, size: Size) : Option {
            return Option(color, size)
        }

        fun 옵션_생성_COLOR_SIZE_PRODUCTID(color: Color, size: Size, productId: Long) : Option {
            var option = Option(color, size)
            option.productId = productId

            return option
        }
    }

}