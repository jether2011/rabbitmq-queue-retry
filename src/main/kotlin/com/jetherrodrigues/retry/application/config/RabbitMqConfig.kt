package com.jetherrodrigues.retry.application.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class RabbitMqConfig(private val connectionFactory: ConnectionFactory) {
    @Bean
    fun simpleRabbitListenerContainerFactory(
        configurator: SimpleRabbitListenerContainerFactoryConfigurer
    ): SimpleRabbitListenerContainerFactory {
        val simpleFactory = SimpleRabbitListenerContainerFactory()
        simpleFactory.setConnectionFactory(connectionFactory)
        simpleFactory.setMessageConverter(messageConverter())
        configurator.configure(simpleFactory, connectionFactory)
        return simpleFactory
    }

    @Bean
    fun rabbitTemplate(): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = messageConverter()
        return rabbitTemplate
    }

    @Bean
    fun messageConverter(): Jackson2JsonMessageConverter {
        val mapper = Jackson2ObjectMapperBuilder
            .json()
            .modules(JavaTimeModule())
            .modules(KotlinModule())
            .dateFormat(StdDateFormat())
            .build<ObjectMapper>()
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        return Jackson2JsonMessageConverter(mapper)
    }
}
