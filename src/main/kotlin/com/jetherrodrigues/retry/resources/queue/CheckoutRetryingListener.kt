package com.jetherrodrigues.retry.resources.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.jetherrodrigues.retry.application.config.queue.CheckoutConfig.Companion.PARKING_LOT_QUEUE
import com.jetherrodrigues.retry.application.config.queue.CheckoutConfig.Companion.PRIMARY_QUEUE
import com.jetherrodrigues.retry.domain.checkout.Checkout
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class CheckoutRetryingListener(
    private val mapper: ObjectMapper,
    private val rabbitTemplate: RabbitTemplate
) {
    private val logger: Logger = LoggerFactory.getLogger(CheckoutRetryingListener::class.java)

    @RabbitListener(queues = [PRIMARY_QUEUE])
    @Throws(Exception::class)
    fun primary(message: Message) {
        val checkout = mapper.readValue(message.body, Checkout::class.java)
        logger.info("Message read from workerQueue: [ $message ] and data object: [ $checkout ]")

        if (hasExceededRetryCount(message)) {
            putIntoParkingLot(message)
            return
        }
        throw Exception("There was an error")
    }

    private fun hasExceededRetryCount(message: Message): Boolean {
        val xDeathHeader: List<Map<String, *>>? = message.messageProperties.xDeathHeader
        if (xDeathHeader != null && xDeathHeader.isNotEmpty()) {
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