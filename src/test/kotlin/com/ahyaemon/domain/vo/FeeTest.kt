package com.ahyaemon.domain.vo

import kotlin.test.Test
import kotlin.test.assertEquals

class FeeTest {

    @Test
    fun testValue() {
        val value = 100
        val fee = Fee.from(value)

        assertEquals(value, fee.value)
    }
}
