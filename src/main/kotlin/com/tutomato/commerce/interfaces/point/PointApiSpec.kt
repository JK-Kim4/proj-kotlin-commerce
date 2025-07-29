package com.tutomato.commerce.interfaces.point

import org.springframework.http.ResponseEntity
import java.math.BigInteger

interface PointApiSpec {

    fun point(authorization: PointRequest.Token) : ResponseEntity<PointResponse.Point>

    fun charge(authorization: PointRequest.Token, point: BigInteger) : ResponseEntity<PointResponse.Point>

}