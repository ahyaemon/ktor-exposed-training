package com.ahyaemon.domain.vo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AddressTest {

    @Test
    fun testValid() {
        val value = "あ".repeat(1000)
        val address = Address.from(value)

        assertEquals(value, address.value)
    }

    @Test
    fun testInvalid_empty() {
        assertFailsWith<IllegalArgumentException> {
            Address.from("")
        }
    }

    @Test
    fun testInvalid_whitespace() {
        assertFailsWith<IllegalArgumentException> {
            Address.from(" ")
        }
    }

    @Test
    fun testInvalid_length() {
        assertFailsWith<IllegalArgumentException> {
            Address.from("あ".repeat(1001))
        }
    }
}