package com.ahyaemon.domain.vo

import kotlin.test.Test
import kotlin.test.assertEquals

class BillingAmountTest {

    @Test
    fun testValue() {
        val paymentAmountValue = 1000
        val paymentAmount = PaymentAmount.from(paymentAmountValue)

        val feeValue = 100
        val fee = Fee.from(feeValue)

        val consumptionTaxValue = 10
        val consumptionTax = ConsumptionTax.from(consumptionTaxValue)

        val billingAmount = BillingAmount.from(
            paymentAmount,
            fee,
            consumptionTax,
        )

        val actual = paymentAmountValue + feeValue + consumptionTaxValue
        assertEquals(actual, billingAmount.value)
    }
}
