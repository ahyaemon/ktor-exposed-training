package com.ahyaemon.domain.vo

import com.ahyaemon.domain.ULIDProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SupplierIdTest {

    @Test
    fun testValidString() {
        val ulid = ULIDProvider.generate()
        val supplierId = SupplierId.from(ulid)

        assertEquals(ulid, supplierId.value)
    }

    @Test
    fun testInvalidString() {
        val invalidUlid = "123"
        assertFailsWith<IllegalArgumentException> {
            SupplierId.from(invalidUlid)
        }
    }
}
