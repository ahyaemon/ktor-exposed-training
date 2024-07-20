package com.ahyaemon.domain.vo

import kotlinx.datetime.LocalDate
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import kotlin.test.*

@RunWith(Enclosed::class)
class PaymentDueDateTest {

    class TestFrom {

        @Test
        fun testValidString() {
            val paymentDueDate = PaymentDueDate.from("2024-06-01")

            assertEquals(LocalDate(2024, 6, 1), paymentDueDate.value)
        }

        @Test
        fun testInvalidString() {
            assertFailsWith<IllegalArgumentException> {
                PaymentDueDate.from("aaa")
            }
            assertFailsWith<IllegalArgumentException> {
                PaymentDueDate.from("2024-06-1")
            }
            assertFailsWith<IllegalArgumentException> {
                PaymentDueDate.from("2024-13-01")
            }
        }
    }

    class TestIsAfter {
        @Test
        fun testAfter() {
            val date1 = PaymentDueDate.from("2024-06-01")
            val date2 = PaymentDueDate.from("2024-06-02")
            assertTrue { date1.isAfter(date2) }
        }

        @Test
        fun testSameDate() {
            val date1 = PaymentDueDate.from("2024-06-01")
            val date2 = PaymentDueDate.from("2024-06-01")
            assertFalse { date1.isAfter(date2) }
        }

        @Test
        fun testBefore() {
            val date1 = PaymentDueDate.from("2024-06-01")
            val date2 = PaymentDueDate.from("2024-05-31")
            assertFalse { date1.isAfter(date2) }
        }
    }
}
