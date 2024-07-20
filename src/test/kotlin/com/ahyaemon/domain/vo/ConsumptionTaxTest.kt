package com.ahyaemon.domain.vo

import kotlin.test.Test
import kotlin.test.assertEquals

class ConsumptionTaxTest {

    @Test
    fun testValue() {
        val value = 100
        val consumptionTax = ConsumptionTax.from(value)

        assertEquals(value, consumptionTax.value)
    }
}
