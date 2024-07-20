package com.ahyaemon.domain.vo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PhoneNumberTest {

    @Test
    fun testValid() {
        val value = "080-1234-5678"
        val phoneNumber = PhoneNumber.from(value)

        assertEquals(value, phoneNumber.value)
    }

    @Test
    fun testInvalid() {
        assertFailsWith<IllegalArgumentException> {
            PhoneNumber.from("aaabbbbcccc")
        }
    }
}
