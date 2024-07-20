package com.ahyaemon.domain.vo

import com.ahyaemon.domain.ULIDProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class InvoiceIdTest {

    @Test
    fun testValidString() {
        val ulid = ULIDProvider.generate()

        val invoiceId = InvoiceId.from(ulid)

        assertEquals(ulid, invoiceId.value)
    }

    @Test
    fun testInvalidString() {
        val invalidUlid = "123"
        assertFailsWith<IllegalArgumentException> {
            InvoiceId.from(invalidUlid)
        }
    }

    @Test
    fun testRandom() {
        val invoiceId = InvoiceId.random()
        assertTrue (ULIDProvider.isValid(invoiceId.value))
    }
}
