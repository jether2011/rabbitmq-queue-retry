package com.jetherrodrigues.retry.application.resources

import com.jetherrodrigues.retry.application.resources.request.CheckoutRequest
import com.jetherrodrigues.retry.application.resources.request.CheckoutRequestMapper
import com.jetherrodrigues.retry.application.resources.response.CheckoutResponse
import com.jetherrodrigues.retry.domain.checkout.Checkout
import com.jetherrodrigues.retry.domain.common.Producer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/v1/api/checkout")
class CheckoutController(
    @Qualifier("checkoutProducer")
    private val checkoutProducer: Producer<Checkout>,
    private val checkoutRequestMapper: CheckoutRequestMapper
) {
    @PostMapping("")
    fun doCheckout(@RequestBody @Validated request: CheckoutRequest): ResponseEntity<CheckoutResponse> =
        checkoutRequestMapper.mapTo(request)
            .let {
                checkoutProducer.send(it)

                ResponseEntity
                    .created(URI("/checkout/${it.id}/detail"))
                    .body(CheckoutResponse(it.id))
            }
}