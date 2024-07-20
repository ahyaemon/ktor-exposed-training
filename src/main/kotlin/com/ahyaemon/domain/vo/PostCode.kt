package com.ahyaemon.domain.vo

/**
 * 郵便番号。
 */
data class PostCode(
    val value: String,
) {

    companion object {

        fun from(
            s: String,
        ): PostCode {
            // 簡易的に、全て数字かチェック
            try {
                s.replace("-", "").toDouble()
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Invalid post code: $s")
            }

            return PostCode(value = s)
        }
    }
}
