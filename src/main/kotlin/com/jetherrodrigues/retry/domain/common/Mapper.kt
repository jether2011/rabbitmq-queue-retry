package com.jetherrodrigues.retry.domain.common

interface Mapper<I, O> {
    fun mapTo(from: I): O
}