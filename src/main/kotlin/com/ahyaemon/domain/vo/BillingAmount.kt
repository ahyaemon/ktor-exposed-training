package com.ahyaemon.domain.vo

/**
 * 請求金額。
 */
data class BillingAmount(
    val value: Int,
) {

    companion object {

        fun from(
            paymentAmount: PaymentAmount,
            fee: Fee,
            consumptionTax: ConsumptionTax,
        ): BillingAmount {
            val value = paymentAmount.value + fee.value + consumptionTax.value

            return BillingAmount(value = value)
        }

        fun from(i: Int): BillingAmount {
            return BillingAmount(value = i)
        }
    }
}
