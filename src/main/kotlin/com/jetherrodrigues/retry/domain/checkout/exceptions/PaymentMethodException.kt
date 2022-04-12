package com.jetherrodrigues.retry.domain.checkout.exceptions

class PaymentMethodException(override val message: String) : RuntimeException(message)