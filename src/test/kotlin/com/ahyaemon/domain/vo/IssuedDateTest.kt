package com.ahyaemon.domain.vo

import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class IssuedDateTest {

    @Test
    fun testValidString() {
        val issuedDate = IssuedDate.from("2024-06-01")

        assertEquals(LocalDate(2024, 6, 1), issuedDate.value)
    }

    @Test
    fun testInvalidString() {
        assertFailsWith<IllegalArgumentException> {
            IssuedDate.from("aaa")
        }
        assertFailsWith<IllegalArgumentException> {
            IssuedDate.from("2024-06-1")
        }
        assertFailsWith<IllegalArgumentException> {
            IssuedDate.from("2024-13-01")
        }
    }
}
