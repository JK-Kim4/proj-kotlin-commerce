package com.tutomato.commerce.support.domain

import com.tutomato.commerce.domain.product.Color
import com.tutomato.commerce.domain.product.Option
import com.tutomato.commerce.domain.product.Size
import com.tutomato.commerce.domain.product.Stock

class OptionDomainSupport {

    fun 기본_옵션_생성() : Option{
        return Option(Color("#ffffff"), Size.XS, Stock(10))
    }

    fun 옵션_생성_COLOR_SIZE(color: Color, size: Size) : Option {
        return Option(color, size)
    }

}