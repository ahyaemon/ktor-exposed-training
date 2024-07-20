package com.ahyaemon.domain.vo

import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ConsumptionTaxRateTest {

    @Test
    fun testValidValue() {
        val value = "0.1"
        val consumptionTaxRate = ConsumptionTaxRate.from(value)

        assertEquals(BigDecimal(value), consumptionTaxRate.value)
    }

    @Test
    fun testInvalidValue() {
        assertFailsWith<IllegalArgumentException> {
            ConsumptionTaxRate.from("a")
        }
    }

    @Test
    fun testCalculate() {
        val consumptionTaxRate = ConsumptionTaxRate.from("0.1")
        val fee = Fee.from(100)
        val tax = consumptionTaxRate.calculateTax(fee)

        val actual = ConsumptionTax.from(10)
        assertEquals(actual.value, tax.value)
    }
}
