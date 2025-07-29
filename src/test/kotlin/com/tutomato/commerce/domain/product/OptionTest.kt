package com.tutomato.commerce.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OptionTest() {

    private lateinit var option: Option

    @BeforeEach
    fun setup() {
        option = Option(
            color = Color("#ff0000"),
            size = Size.L,
            stock = Stock(10),
        )
    }

    @Test
    fun `옵션_조건이_모두_일치하면_true를_반환한다`() {
        //given
        val equalOption = Option(
            color = Color("#ff0000"),
            size = Size.L,
            stock = Stock(10),
        )

        //when then
        assertThat(option.matches(equalOption)).isTrue()
    }

    @Test
    fun `옵션_일부_조건이_null이면_해당_조건을_무시하고_비교한다`() {
        //given
        val nullFieldOption = Option(
            color = Color("#ff0000"),
            size = null,
            stock = Stock(10),
        )

        //when then
        assertThat(option.matches(nullFieldOption)).isTrue()
    }

    @Test
    fun `옵션_조건이_일치하지_않으면_false를_반환한다`() {
        //given
        val notEqualOption = Option(
            color = Color("#ffffff"),
            size = Size.XS,
            stock = Stock(10),
        )

        //when then
        assertThat(option.matches(notEqualOption)).isFalse()
    }

    @Test
    fun `사이즈 일치 여부를 반환한다`() {
        assertThat(option.isEqualSize(Size.L)).isTrue()
        assertThat(option.isEqualSize(Size.M)).isFalse()
    }

    @Test
    fun `색상 일치 여부를 반환한다`() {
        assertThat(option.isEqualColor(Color("#ff0000"))).isTrue()
    }

}