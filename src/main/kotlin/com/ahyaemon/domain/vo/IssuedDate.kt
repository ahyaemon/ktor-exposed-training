package com.ahyaemon.domain.vo

import kotlinx.datetime.LocalDate

/**
 * 請求書発行日。
 */
data class IssuedDate (
    val value: LocalDate,
) {

    companion object {

        fun from(s: String): IssuedDate {
            val value = try {
                LocalDate.parse(s)
            } catch (e: Exception) {
                throw IllegalArgumentException("Invalid issued date: $s")
            }
            return IssuedDate(value = value)
        }
    }
}
