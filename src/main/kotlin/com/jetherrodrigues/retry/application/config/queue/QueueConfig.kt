package com.jetherrodrigues.retry.application.config.queue

import com.jetherrodrigues.retry.domain.checkout.Checkout
import com.jetherrodrigues.retry.domain.common.Producer
import com.jetherrodrigues.retry.resources.queue.CheckoutProducer
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueueConfig(private val rabbitTemplate: RabbitTemplate) {
    @Bean
    @Qualifier("checkoutProducer")
    fun checkoutProducer(): Producer<Checkout> = CheckoutProducer(rabbitTemplate)
}
