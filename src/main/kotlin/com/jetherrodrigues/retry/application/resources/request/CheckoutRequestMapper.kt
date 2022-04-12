package com.jetherrodrigues.retry.application.resources.request

import com.jetherrodrigues.retry.domain.checkout.Checkout
import com.jetherrodrigues.retry.domain.checkout.PaymentMethod
import com.jetherrodrigues.retry.domain.common.Mapper
import org.springframework.stereotype.Component

@Component
class CheckoutRequestMapper : Mapper<CheckoutRequest, Checkout> {
    override fun mapTo(from: CheckoutRequest): Checkout = Checkout(
        paymentMethod = PaymentMethod.from(from.paymentMethod),
        amount = from.amount,
        personId = from.personId
    )
}
