package com.tutomato.commerce.interfaces.ranking

import com.tutomato.commerce.application.ranking.RankingFacade
import com.tutomato.commerce.domain.ranking.RankingCriteria
import com.tutomato.commerce.domain.ranking.RankingResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ranking")
class RankingApiController(
    private val rankingFacade: RankingFacade
) {

    @GetMapping
    fun rankings(criteria: RankingCriteria.Ranking): ResponseEntity<RankingResult.Rankings> {
        return ResponseEntity.ok(rankingFacade.getRanking(criteria))
    }
}