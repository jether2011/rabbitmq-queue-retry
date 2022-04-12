package com.jetherrodrigues.retry.resources.queue

import com.jetherrodrigues.retry.application.config.queue.CheckoutConfig
import com.jetherrodrigues.retry.domain.checkout.Checkout
import com.jetherrodrigues.retry.domain.common.Producer
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.rabbit.core.RabbitTemplate

class CheckoutProducer(private val rabbitTemplate: RabbitTemplate): Producer<Checkout> {
    override fun send(message: Checkout) {
        try {
            rabbitTemplate.convertAndSend(
                CheckoutConfig.EXCHANGE_NAME,
                CheckoutConfig.PRIMARY_ROUTING_KEY,
                message
            )
        } catch (ex: Exception) {
            throw AmqpRejectAndDontRequeueException(ex)
        }
    }
}
