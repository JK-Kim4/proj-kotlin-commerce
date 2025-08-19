package com.tutomato.commerce.application.ranking

import com.tutomato.commerce.domain.order.OrderPeriod
import com.tutomato.commerce.domain.ranking.RankingCriteria
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime


@SpringBootTest
@Testcontainers
@Sql(scripts = ["classpath:data/ranking.sql"])
class RankingFacadeTest(
    @Autowired private val rankingFacade: RankingFacade,
) {

    @Test
    fun `최근한달 판매 상품 순위 조회`() {
        //given
        val criteria = RankingCriteria.Ranking(
            period = OrderPeriod.ONE_MONTH,
            calculatedAt = LocalDateTime.of(2025,1,1,0,0,0),
        )

        //when
        val rankings = rankingFacade.getRanking(criteria)

        //then
        assertThat(rankings).isNotNull
        assertThat(rankings.ranking.size).isEqualTo(5)
        assertThat(rankings.ranking.map { it.productName })
            .containsExactly(
                "월간 판매량 1위 상품 - 10개",
                "월간 판매량 2위 상품 - 8개",
                "월간 판매량 3위 상품 - 6개",
                "월간 판매량 4위 상품 - 4개",
                "월간 판매량 5위 상품 - 2개"
            )
        assertThat(rankings.ranking.map { it.productName })
            .doesNotContain("판매량 미집계 상품 - 과거 판매 이력")
    }

}