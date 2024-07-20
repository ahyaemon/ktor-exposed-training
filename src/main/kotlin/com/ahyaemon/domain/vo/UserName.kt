package com.ahyaemon.domain.vo

/**
 * ユーザー名。
 */
data class UserName(
    val value: String,
) {

    companion object {

        private const val MAX_LENGTH = 100

        fun from(
            s: String,
        ): UserName {
            if (s.isBlank()) {
                throw IllegalArgumentException("UserName cannot be empty")
            }
            if (s.length > MAX_LENGTH) {
                throw IllegalArgumentException("UserName cannot exceed $MAX_LENGTH")
            }

            return UserName(value = s)
        }
    }
}
