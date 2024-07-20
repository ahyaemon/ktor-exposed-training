package com.ahyaemon.domain.vo

/**
 * 手数料の金額。
 * 手数料率（FeeRate）ではない。
 */
data class Fee(
    val value: Int,
) {

    companion object {

        fun from(i: Int): Fee {
            return Fee(value = i)
        }
    }
}
