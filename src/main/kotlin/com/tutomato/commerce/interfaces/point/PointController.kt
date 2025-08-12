package com.tutomato.commerce.interfaces.point

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigInteger

@RestController
@RequestMapping("/api/points")
class PointController: PointApiSpec {

    @GetMapping("/me")
    override fun point(
        @RequestHeader("Authorization") authorization: PointRequest.Token
    ): ResponseEntity<PointResponse.Point> {
        return ResponseEntity.ok(PointResponse.Point(1L, BigInteger.valueOf(50_000)))
    }

    @PostMapping("/charge")
    override fun charge(
        @RequestHeader("Authorization") authorization: PointRequest.Token,
        @RequestBody point: BigInteger
    ): ResponseEntity<PointResponse.Point> {
        return ResponseEntity.ok(PointResponse.Point(1L, BigInteger.valueOf(55_000)))
    }

}