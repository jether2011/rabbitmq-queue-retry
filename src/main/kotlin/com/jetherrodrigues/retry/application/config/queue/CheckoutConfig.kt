package com.jetherrodrigues.retry.application.config.queue

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CheckoutConfig {
    companion object {
        const val EXCHANGE_NAME = "retry.exchange"
        const val PRIMARY_QUEUE = "checkout.queue"
        const val WAIT_QUEUE = "$PRIMARY_QUEUE.wait"
        const val PARKING_LOT_QUEUE = "$PRIMARY_QUEUE.parkingLot"
        const val PRIMARY_ROUTING_KEY = "checkout.routing.key"
    }

    @Bean
    fun exchange(): DirectExchange {
        return DirectExchange(EXCHANGE_NAME)
    }

    @Bean
    fun primaryQueue(): Queue {
        return QueueBuilder.durable(PRIMARY_QUEUE)
            .deadLetterExchange(EXCHANGE_NAME)
            .deadLetterRoutingKey(WAIT_QUEUE)
            .build()
    }

    @Bean
    fun waitQueue(): Queue {
        return QueueBuilder.durable(WAIT_QUEUE)
            .deadLetterExchange(EXCHANGE_NAME)
            .deadLetterRoutingKey(PRIMARY_ROUTING_KEY)
            .ttl(10000)
            .build()
    }

    @Bean
    fun parkingLotQueue(): Queue {
        return Queue(PARKING_LOT_QUEUE)
    }

    @Bean
    fun primaryBinding(primaryQueue: Queue, exchange: DirectExchange): Binding {
        return BindingBuilder.bind(primaryQueue).to(exchange).with(PRIMARY_ROUTING_KEY)
    }

    @Bean
    fun waitBinding(waitQueue: Queue, exchange: DirectExchange): Binding {
        return BindingBuilder.bind(waitQueue).to(exchange).with(WAIT_QUEUE)
    }

    @Bean
    fun parkingBinding(parkingLotQueue: Queue, exchange: DirectExchange): Binding {
        return BindingBuilder.bind(parkingLotQueue).to(exchange).with(PARKING_LOT_QUEUE)
    }
}
