package com.ahyaemon.domain.vo

import com.ahyaemon.domain.ULIDProvider

/**
 * 請求書ID。
 */
data class InvoiceId(
    val value: String,
) {

    companion object {

        fun from(s: String): InvoiceId {
            if (!ULIDProvider.isValid(s)) {
                throw IllegalArgumentException("Invoice ID invalid: $s")
            }

            return InvoiceId(value = s)
        }

        fun random(): InvoiceId {
            return InvoiceId(value = ULIDProvider.generate())
        }
    }
}
