package com.ahyaemon.domain.vo

/**
 * ハッシュ化されていない生のパスワード。
 * **絶対にログに出してはいけない。**
 */
data class RawPassword(
    val value: String,
) {

    companion object {

        private const val MAX_LENGTH = 100

        fun from(
            s: String,
        ): RawPassword {
            if (s.isBlank()) {
                throw IllegalArgumentException("Password cannot be empty")
            }
            if (s.length > MAX_LENGTH) {
                throw IllegalArgumentException("Password cannot exceed $MAX_LENGTH")
            }

            return RawPassword(value = s)
        }
    }
}
