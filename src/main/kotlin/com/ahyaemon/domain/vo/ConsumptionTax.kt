package com.ahyaemon.domain.vo

/**
 * 消費税の金額。
 * 消費税率（ConsumptionTaxRate）ではない。
 */
data class ConsumptionTax(
    val value: Int,
) {

    companion object {

        fun from(i: Int): ConsumptionTax {
            return ConsumptionTax(value = i)
        }
    }
}
