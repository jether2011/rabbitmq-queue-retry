package com.jetherrodrigues.retry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QueueRetryApplication

fun main(args: Array<String>) {
	runApplication<QueueRetryApplication>(*args)
}
