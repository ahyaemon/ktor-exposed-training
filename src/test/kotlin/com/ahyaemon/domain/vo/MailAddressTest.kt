package com.ahyaemon.domain.vo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MailAddressTest {

    @Test
    fun testValid() {
        val value = "a".repeat(100)
        val mailAddress = MailAddress.from(value)

        assertEquals(value, mailAddress.value)
    }

    @Test
    fun testInvalid_empty() {
        assertFailsWith<IllegalArgumentException> {
            MailAddress.from("")
        }
    }

    @Test
    fun testInvalid_whitespace() {
        assertFailsWith<IllegalArgumentException> {
            MailAddress.from(" ")
        }
    }

    @Test
    fun testInvalid_length() {
        assertFailsWith<IllegalArgumentException> {
            MailAddress.from("あ".repeat(101))
        }
    }
}