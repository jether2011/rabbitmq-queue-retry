package com.jetherrodrigues.retry.domain.common

interface Producer<T> {
    fun send(message: T)
}
