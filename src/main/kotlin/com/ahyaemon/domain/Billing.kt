package com.ahyaemon.domain

import com.ahyaemon.domain.vo.*

/**
 * 請求額関連をまとめたクラス。
 */
data class Billing (
    val amount: BillingAmount,
    val fee: Fee,
    val feeRate: FeeRate,
    val consumptionTax: ConsumptionTax,
    val consumptionTaxRate: ConsumptionTaxRate,
) {

    companion object {

        fun from(
            paymentAmount: PaymentAmount,
            feeRate: FeeRate,
            consumptionTaxRate: ConsumptionTaxRate,
        ): Billing {
            val fee = feeRate.calculateFee(paymentAmount)
            val consumptionTax = consumptionTaxRate.calculateTax(fee)
            val billingAmount = BillingAmount.from(
                paymentAmount,
                fee,
                consumptionTax
            )

            return Billing(
                billingAmount,
                fee,
                feeRate,
                consumptionTax,
                consumptionTaxRate,
            )
        }
    }
}
