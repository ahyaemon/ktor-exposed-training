package com.ahyaemon.domain.vo

import com.ahyaemon.domain.ULIDProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CompanyIdTest {

    @Test
    fun testValidString() {
        val ulid = ULIDProvider.generate()
        val companyId = CompanyId.from(ulid)

        assertEquals(ulid, companyId.value)
    }

    @Test
    fun testInvalidString() {
        val invalidUlid = "123"
        assertFailsWith<IllegalArgumentException> {
            CompanyId.from(invalidUlid)
        }
    }
}
