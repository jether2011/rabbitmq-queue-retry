package com.jetherrodrigues.retry.application.resources.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class CheckoutRequest(
    @field:NotBlank val personId: String,
    @field:NotBlank val paymentMethod: String,
    @field:NotNull
    @field:Positive
    val amount: Double
)
