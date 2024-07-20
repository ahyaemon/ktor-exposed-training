package com.ahyaemon.domain.vo

import com.ahyaemon.domain.BCryptHashProvider

/**
 * ハッシュ化されたパスワード。
 */
data class PasswordHash(
    val value: String,
) {

    companion object {

        fun from(
            rawPassword: RawPassword,
        ): PasswordHash {
            val value = BCryptHashProvider.generate(rawPassword.value)
            return PasswordHash(value = value)
        }
    }
}
