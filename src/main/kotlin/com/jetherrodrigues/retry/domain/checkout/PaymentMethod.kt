package com.jetherrodrigues.retry.domain.checkout

import com.jetherrodrigues.retry.domain.checkout.exceptions.PaymentMethodException

enum class PaymentMethod {
    CREDIT,
    DEBIT;

    companion object {
        fun from(value: String): PaymentMethod = values().firstOrNull { it.name == value }
            ?: throw PaymentMethodException("Unknown Payment Method [ $value ]")
    }
}