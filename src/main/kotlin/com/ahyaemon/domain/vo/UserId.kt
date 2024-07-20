package com.ahyaemon.domain.vo

import com.ahyaemon.domain.ULIDProvider

/**
 * ユーザーID。
 */
data class UserId(
    val value: String,
) {

    companion object {

        fun from(s: String): UserId {
            if (!ULIDProvider.isValid(s)) {
                throw IllegalArgumentException("User ID invalid: $s")
            }

            return UserId(value = s)
        }

        fun random(): UserId {
            return UserId(value = ULIDProvider.generate())
        }
    }
}
