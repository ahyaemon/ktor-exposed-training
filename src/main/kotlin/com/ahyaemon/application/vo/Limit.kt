package com.ahyaemon.application.vo

/**
 * データの取得件数。
 */
data class Limit(
    val value: Int,
) {

    companion object {

        fun from(s: String?): Limit {
            if (s.isNullOrEmpty()) {
                throw IllegalArgumentException("Limit value can not be null.")
            }

            val i = try {
                s.toInt()
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Invalid limit format: $s")
            }

            if (i < 0) {
                throw IllegalArgumentException("Limit value must be greater equal than zero.")
            }

            return Limit(value = i)
        }
    }
}
