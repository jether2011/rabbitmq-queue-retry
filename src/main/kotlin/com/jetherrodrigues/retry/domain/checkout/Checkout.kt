package com.jetherrodrigues.retry.domain.checkout

import io.azam.ulidj.ULID

data class Checkout(
    val id: String = ULID.random(),
    val personId: String,
    val amount: Double,
    val paymentMethod: PaymentMethod
) {
    override fun toString(): String = """
        Checkout {
            id: $id,
            personId: $personId,
            amount: $amount,
            paymentMethod: $paymentMethod
        }
    """.trimIndent()
}
