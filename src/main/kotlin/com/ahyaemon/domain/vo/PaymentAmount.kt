package com.ahyaemon.domain.vo

/**
 * 支払い金額。
 */
data class PaymentAmount(
    val value: Int,
) {

    companion object {
        fun from(i: Int): PaymentAmount {
            return PaymentAmount(i)
        }
    }
}
