package com.ahyaemon.domain.vo

import kotlin.test.Test
import kotlin.test.assertEquals

class PaymentAmountTest {

    @Test
    fun testValue() {
        val value = 100
        val paymentAmount = PaymentAmount.from(value)

        assertEquals(value, paymentAmount.value)
    }
}
