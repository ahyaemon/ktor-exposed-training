package com.ahyaemon.domain.vo

import com.ahyaemon.domain.ULIDProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserIdTest {

    @Test
    fun testValidString() {
        val ulid = ULIDProvider.generate()
        val userId = UserId.from(ulid)

        assertEquals(ulid, userId.value)
    }

    @Test
    fun testInvalidString() {
        val invalidUlid = "123"
        assertFailsWith<IllegalArgumentException> {
            UserId.from(invalidUlid)
        }
    }
}
