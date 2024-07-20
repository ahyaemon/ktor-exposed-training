package com.ahyaemon.domain.vo

/**
 * 住所。
 */
data class Address(
    val value: String,
) {

    companion object {

        private const val MAX_LENGTH = 1000

        fun from(
            s: String,
        ): Address {
            if (s.isBlank()) {
                throw IllegalArgumentException("Address cannot be empty")
            }
            if (s.length > MAX_LENGTH) {
                throw IllegalArgumentException("Address cannot exceed $MAX_LENGTH")
            }

            return Address(value = s)
        }
    }
}
