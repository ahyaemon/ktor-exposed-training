package com.ahyaemon.domain.vo

import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FeeRateTest {

    @Test
    fun testValidValue() {
        val value = "0.1"
        val feeRate = FeeRate.from(value)

        assertEquals(BigDecimal(value), feeRate.value)
    }

    @Test
    fun testInvalidValue() {
        assertFailsWith<IllegalArgumentException> {
            FeeRate.from("a")
        }
    }

    @Test
    fun testCalculate() {
        val feeRate = FeeRate.from("0.1")

        val paymentAmount = PaymentAmount.from(100)
        val fee = feeRate.calculateFee(paymentAmount)

        val actual = Fee.from(10)
        assertEquals(actual.value, fee.value)
    }
}
