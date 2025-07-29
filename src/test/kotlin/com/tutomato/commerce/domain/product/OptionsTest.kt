package com.tutomato.commerce.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OptionsTest {

    private lateinit var options: Options
    val newOption = Option(Color("#100402"), Size.XS, Stock(10))
    val existOption = Option(Color("#ffffff"), Size.XS, Stock(10))

    @BeforeEach
    fun init() {
        this.options = Options(
            listOf(
                Option(Color("#ffffff"), Size.XS, Stock(10)),
                Option(Color("#000000"), Size.S, Stock(10)),
                Option(Color("#111111"), Size.M, Stock(10)),
                Option(Color("#ffffff"), Size.L, Stock(50)),
            )
        )
    }

    @Test
    fun `전달받은 옵션 정보와 일치하는 옵션의 현재 재고를 반환한다`() {
        val color = Color("#ffffff")
        val size = Size.L

        assertThat(options.getStockByOption(Option(color, size)))
            .isEqualTo(Stock(50))
    }

    @Test
    fun `존재하지 않는 옵션에 대한 현재 재고 조회 요청시 오류 반환`() {
        val color = Color("nonexistcolor")
        val size = Size.L

        assertThatThrownBy { options.getStockByOption(Option(color, size)) }
            .isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun `새로운 옵션을 추가(add)하면 기존 리스트에 추가된 새로운 객체가 생성된다`() {
        options = options.add(newOption)

        assertThat(options.size()).isEqualTo(5)
        assertThat(options.optionAt(options.size()-1))
            .isEqualTo(newOption)
    }

    @Test
    fun `이미 등록된 옵션과 동일한 옵션을 추가할 경우 오류가 발생한다`() {
        assertThatThrownBy {
            options.add(existOption)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `옵션 목록에서 일치하는 옵션을 제거한다`() {
        val targetOption = Option(Color("#000000"), Size.S, Stock(10))

        options = options.remove(targetOption)

        assertThat(options.size()).isEqualTo(3)
        assertThat(options.hasEqualOption(targetOption)).isFalse()
    }

    @Test
    fun `동일한 옵션이 존재할 경우 hasEqualOption는 true를 반환한다`() {
        assertThat(options.hasEqualOption(existOption)).isTrue()
    }

    @Test
    fun `조건을 충족하는 옵션이 존재할 경우 true를 반환한다`() {
        val 조건_충족_옵션 = Option(Color("#ffffff"), Size.XS, Stock(10))

        assertThat(options.hasMatchesOption(조건_충족_옵션)).isTrue()
    }

    @Test
    fun `옵션_일부_조건이_null이면_해당_조건을_무시하고_비교한다`() {
        //given
        val 널_포함_조건_충족_옵션 = Option(null, Size.XS, Stock(10))

        //when then
        assertThat(options.hasMatchesOption(널_포함_조건_충족_옵션)).isTrue()
    }

    @Test
    fun `충족하는 조건이 존재하지 않을 경우 false를 반환한다`() {
        //given
        val 조건_미충족_옵션 = Option(Color("f123456"), Size.XS, Stock(10))

        //when then
        assertThat(options.hasMatchesOption(조건_미충족_옵션)).isFalse()
    }
}