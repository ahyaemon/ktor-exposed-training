package com.ahyaemon.application.vo

/**
 * データのトータル件数。
 */
data class Total(
    val value: Long,
) {

    companion object {

        fun from(i: Long): Total {
            if (i < 0) {
                throw IllegalArgumentException("Total value must be greater equal than zero.")
            }

            return Total(value = i)
        }
    }
}
