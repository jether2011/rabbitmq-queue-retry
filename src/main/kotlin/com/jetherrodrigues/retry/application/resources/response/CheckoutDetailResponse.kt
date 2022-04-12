package com.jetherrodrigues.retry.application.resources.response

import com.jetherrodrigues.retry.domain.checkout.Checkout
import com.jetherrodrigues.retry.domain.checkout.PaymentMethod

data class CheckoutDetailResponse(
    val id: String,
    val personId: String,
    val amount: Double,
    val paymentMethod: PaymentMethod
) {
    companion object {
        fun from(checkout: Checkout) = CheckoutDetailResponse(
            id = checkout.id,
            personId = checkout.personId,
            amount = checkout.amount,
            paymentMethod = checkout.paymentMethod
        )
    }
}
