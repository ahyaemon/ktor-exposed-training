package com.ahyaemon.domain.vo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PostCodeTest {

    @Test
    fun testValid() {
        val value = "012-3456"
        val postCode = PostCode.from(value)

        assertEquals(value, postCode.value)
    }

    @Test
    fun testInvalid() {
        assertFailsWith<IllegalArgumentException> {
            PostCode.from("aaabbbb")
        }
    }
}
