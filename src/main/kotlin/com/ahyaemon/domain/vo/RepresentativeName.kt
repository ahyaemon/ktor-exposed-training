package com.ahyaemon.domain.vo

/**
 * 代表者名。
 */
data class RepresentativeName(
    val value: String,
) {

    companion object {

        private const val MAX_LENGTH = 100

        fun from(
            s: String,
        ): RepresentativeName {
            if (s.isBlank()) {
                throw IllegalArgumentException("CorporateName cannot be empty")
            }
            if (s.length > MAX_LENGTH) {
                throw IllegalArgumentException("CorporateName cannot exceed $MAX_LENGTH")
            }

            return RepresentativeName(value = s)
        }
    }
}
