package com.ahyaemon.domain.vo

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * 手数料率。
 */
data class FeeRate(
    val value: BigDecimal,
) {

    fun calculateFee(paymentAmount: PaymentAmount): Fee {
        val value = BigDecimal(paymentAmount.value)
            .multiply(value)
            .setScale(0, RoundingMode.UP).toInt()
        return Fee.from(value)
    }

    companion object {

        fun from(s: String): FeeRate {
            val value = try {
                BigDecimal(s)
            } catch (e: Exception) {
                throw IllegalArgumentException("Invalid fee value '$s'")
            }

            return FeeRate(value = value)
        }
    }
}
