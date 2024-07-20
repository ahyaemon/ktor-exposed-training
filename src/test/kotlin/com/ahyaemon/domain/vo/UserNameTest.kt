package com.ahyaemon.domain.vo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserNameTest {

    @Test
    fun testValid() {
        val value = "あ".repeat(100)
        val userName = UserName.from(value)

        assertEquals(value, userName.value)
    }

    @Test
    fun testInvalid_empty() {
        assertFailsWith<IllegalArgumentException> {
            UserName.from("")
        }
    }

    @Test
    fun testInvalid_whitespace() {
        assertFailsWith<IllegalArgumentException> {
            UserName.from(" ")
        }
    }

    @Test
    fun testInvalid_length() {
        assertFailsWith<IllegalArgumentException> {
            UserName.from("あ".repeat(101))
        }
    }
}