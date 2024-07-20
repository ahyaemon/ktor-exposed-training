package com.ahyaemon.domain.vo

import kotlinx.datetime.LocalDate

/**
 * 支払い期限日。
 */
data class PaymentDueDate (
    val value: LocalDate,
) {

    fun isAfter(date: PaymentDueDate): Boolean {
        return value < date.value
    }

    companion object {

        fun from(s: String?): PaymentDueDate {
            if (s == null) {
                throw IllegalArgumentException("Payment due date must be set")
            }

            val value = try {
                LocalDate.parse(s)
            } catch (e: Exception) {
                throw IllegalArgumentException("Invalid payment due date: $s")
            }

            return PaymentDueDate(value = value)
        }
    }
}
