package com.ahyaemon.domain.vo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CorporateNameTest {

    @Test
    fun testValid() {
        val value = "あ".repeat(100)
        val corporateName = CorporateName.from(value)

        assertEquals(value, corporateName.value)
    }

    @Test
    fun testInvalid_empty() {
        assertFailsWith<IllegalArgumentException> {
            CorporateName.from("")
        }
    }

    @Test
    fun testInvalid_whitespace() {
        assertFailsWith<IllegalArgumentException> {
            CorporateName.from(" ")
        }
    }

    @Test
    fun testInvalid_length() {
        assertFailsWith<IllegalArgumentException> {
            CorporateName.from("あ".repeat(101))
        }
    }
}