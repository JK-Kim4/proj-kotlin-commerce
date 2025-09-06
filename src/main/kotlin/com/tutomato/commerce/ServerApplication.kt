package com.tutomato.commerce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy
class ServerApplication

fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
}