package com.ahyaemon.domain.vo

/**
 * メールアドレス。
 */
data class MailAddress(
    val value: String,
) {

    companion object {

        private const val MAX_LENGTH = 100

        fun from(
            s: String,
        ): MailAddress {
            // 簡易的にブランクと長さだけチェック
            if (s.isBlank()) {
                throw IllegalArgumentException("MailAddress cannot be empty")
            }
            if (s.length > MAX_LENGTH) {
                throw IllegalArgumentException("MailAddress cannot exceed $MAX_LENGTH")
            }

            return MailAddress(value = s)
        }
    }
}
