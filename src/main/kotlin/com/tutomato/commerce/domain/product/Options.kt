package com.tutomato.commerce.domain.product

class Options(
    val options: List<Option>,
) {

    /*옵션 충족 여부 검증*/
    fun hasMatchesOption(option: Option) : Boolean {
        return options.any { it.matches(option) }
    }

}
