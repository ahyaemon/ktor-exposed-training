package com.ahyaemon.domain.vo

import com.ahyaemon.domain.ULIDProvider

/**
 * 取引先ID。
 */
data class SupplierId(
    val value: String,
) {

    companion object {

        fun from(s: String): SupplierId {
            if (!ULIDProvider.isValid(s)) {
                throw IllegalArgumentException("Supplier ID invalid: $s")
            }

            return SupplierId(value = s)
        }
    }
}
