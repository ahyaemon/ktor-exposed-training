package com.ahyaemon.domain.vo

/**
 * 企業名。
 */
data class CorporateName(
    val value: String,
) {

    companion object {

        private const val MAX_LENGTH = 100

        fun from(
            s: String,
        ): CorporateName {
            if (s.isBlank()) {
                throw IllegalArgumentException("CorporateName cannot be empty")
            }
            if (s.length > MAX_LENGTH) {
                throw IllegalArgumentException("CorporateName cannot exceed $MAX_LENGTH")
            }

            return CorporateName(value = s)
        }
    }
}
