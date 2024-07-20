package com.ahyaemon.domain.vo

/**
 * 電話番号。
 */
data class PhoneNumber(
    val value: String,
) {

    companion object {

        fun from(
            s: String,
        ): PhoneNumber {
            // 簡易的に、全て数字かチェック
            try {
                s.replace("-", "").toDouble()
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Invalid phone number: $s")
            }

            return PhoneNumber(value = s)
        }
    }
}
