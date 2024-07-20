package com.ahyaemon.application.vo

/**
 * データ取得時のオフセット。
 */
data class Offset(
    val value: Int,
) {

    companion object {

        fun from(s: String?): Offset {
            if (s.isNullOrEmpty()) {
                throw IllegalArgumentException("Limit value can not be null.")
            }

            val i = try {
                s.toInt()
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Invalid limit format: $s")
            }

            if (i < 0) {
                throw IllegalArgumentException("Offset value must be greater equal than zero.")
            }

            return Offset(value = i)
        }
    }
}
