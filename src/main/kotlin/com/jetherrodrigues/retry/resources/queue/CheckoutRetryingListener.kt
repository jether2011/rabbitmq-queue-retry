package com.jetherrodrigues.retry.resources.queue

import com.jetherrodrigues.retry.application.config.queue.CheckoutConfig.Companion.PARKING_LOT_QUEUE
import com.jetherrodrigues.retry.application.config.queue.CheckoutConfig.Companion.PRIMARY_QUEUE
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class CheckoutRetryingListener(private val rabbitTemplate: RabbitTemplate) {
    private val logger: Logger = LoggerFactory.getLogger(CheckoutRetryingListener::class.java)

    @RabbitListener(queues = [PRIMARY_QUEUE])
    @Throws(Exception::class)
    fun primary(checkout: Message) {
        logger.info("Message read from workerQueue: $checkout")
        if (hasExceededRetryCount(checkout)) {
            putIntoParkingLot(checkout)
            return
        }
        throw Exception("There was an error")
    }

    private fun hasExceededRetryCount(`in`: Message): Boolean {
        val xDeathHeader: List<Map<String, *>> = `in`.messageProperties.xDeathHeader
        if (xDeathHeader.isNotEmpty()) {
            val count = xDeathHeader[0]["count"] as Long
            return count >= 3
        }
        return false
    }

    private fun putIntoParkingLot(failedMessage: Message) {
        logger.info("Retries exceeded putting into parking lot")
        rabbitTemplate.send(PARKING_LOT_QUEUE, failedMessage)
    }
}