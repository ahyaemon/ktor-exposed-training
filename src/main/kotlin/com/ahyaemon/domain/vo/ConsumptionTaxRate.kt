package com.ahyaemon.domain.vo

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * 消費税率。
 */
data class ConsumptionTaxRate(
    val value: BigDecimal,
) {

    fun calculateTax(fee: Fee): ConsumptionTax {
        val value = BigDecimal(fee.value)
            .multiply(value)
            .setScale(0, RoundingMode.UP).toInt()
        return ConsumptionTax.from(value)
    }

    companion object {

        fun from(s: String): ConsumptionTaxRate {
            val value = try {
                BigDecimal(s)
            } catch (e: Exception) {
                throw IllegalArgumentException("Invalid fee value '$s'")
            }

            return ConsumptionTaxRate(value = value)
        }
    }
}
