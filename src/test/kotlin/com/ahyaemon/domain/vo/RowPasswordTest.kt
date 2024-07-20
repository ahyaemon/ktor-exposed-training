package com.ahyaemon.domain.vo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RowPasswordTest {

    @Test
    fun testValid() {
        val value = "a".repeat(100)
        val password = RawPassword.from(value)

        assertEquals(value, password.value)
    }

    @Test
    fun testInvalid_empty() {
        assertFailsWith<IllegalArgumentException> {
            RawPassword.from("")
        }
    }

    @Test
    fun testInvalid_whitespace() {
        assertFailsWith<IllegalArgumentException> {
            RawPassword.from(" ")
        }
    }

    @Test
    fun testInvalid_length() {
        assertFailsWith<IllegalArgumentException> {
            RawPassword.from("a".repeat(101))
        }
    }
}