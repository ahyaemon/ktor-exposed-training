package com.ahyaemon.domain.vo

import com.ahyaemon.domain.ULIDProvider

/**
 * 企業ID。
 */
data class CompanyId(
    val value: String,
) {

    companion object {

        fun from(s: String): CompanyId {
            if (!ULIDProvider.isValid(s)) {
                throw IllegalArgumentException("Company ID invalid: $s")
            }

            return CompanyId(value = s)
        }

        fun random(): CompanyId {
            return CompanyId(value = ULIDProvider.generate())
        }
    }
}
