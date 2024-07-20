package com.ahyaemon.adapter.routes.invoice

import com.ahyaemon.adapter.DIContainer
import com.ahyaemon.adapter.config.createJWT
import com.ahyaemon.adapter.routes.ValidationErrorResponse
import com.ahyaemon.application.invoice.InvoiceRepository
import com.ahyaemon.domain.vo.InvoiceStatus
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll

import kotlin.test.Test
import kotlin.test.assertEquals

class CreateInvoiceRouteTest {

    private val url = "/invoices"

    private fun createRequest(
        supplierId: String = "0000004JFGVCGQFKE4JWFTZ8QK",
        issuedDate: String = "2024-06-01",
        paymentDueDate: String = "2024-06-30",
        paymentAmount: Int = 10000,
    ): CreateInvoiceRequest = CreateInvoiceRequest(
        supplierId = supplierId,
        issuedDate = issuedDate,
        paymentDueDate = paymentDueDate,
        paymentAmount = paymentAmount,
    )

    private fun createTestJWT(
        claims: Map<String, String>,
        expireSeconds: Int,
    ): String {
        val secret = "secret"
        val issuer = "http://0.0.0.0:18080"
        val audience = "http://0.0.0.0:18080"

        return createJWT(
            secret = secret,
            issuer = issuer,
            audience = audience,
            claims = claims,
            expireSeconds = expireSeconds,
        )
    }

    @Test
    fun test200() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val jwt = createTestJWT(
            mapOf("userId" to "0000004JFG398AF6GEKSVEQFZ4"),
            expireSeconds = 600,
        )

        val request = createRequest()
        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $jwt")
            setBody(request)
        }
        val createInvoiceResponse = response.body<CreateInvoiceResponse>()

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(26, createInvoiceResponse.invoiceId.length)
        assertEquals(request.supplierId, createInvoiceResponse.supplierId)
        assertEquals(request.issuedDate, createInvoiceResponse.issuedDate)
        assertEquals(request.paymentDueDate, createInvoiceResponse.paymentDueDate)
        assertEquals(request.paymentAmount, createInvoiceResponse.paymentAmount)
        assertEquals(InvoiceStatus.UNTREATED.name, createInvoiceResponse.status)

        // TODO 日付で変わりうる値に依存している
        assertEquals(10440, createInvoiceResponse.billing.amount)
        assertEquals(400, createInvoiceResponse.billing.fee)
        assertEquals("0.04", createInvoiceResponse.billing.feeRate)
        assertEquals(40, createInvoiceResponse.billing.consumptionTax)
        assertEquals("0.1", createInvoiceResponse.billing.consumptionTaxRate)
    }

    @Test
    fun test400() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val request = createRequest(
            issuedDate = "aaa",
            paymentDueDate = "bbb",
        )

        val jwt = createTestJWT(
            mapOf("userId" to "0000004JFGM4G8NJXH4QKH8QVJ"),
            expireSeconds = 600,
        )
        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $jwt")
            setBody(request)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)

        val body = response.body<ValidationErrorResponse>()
        assertEquals(2, body.messages.size)
    }

    @Test
    fun test401_expire() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val request = createRequest()

        val jwt = createTestJWT(
            mapOf("userId" to "0000004JFGM4G8NJXH4QKH8QVJ"),
            expireSeconds = 0,
        )

        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $jwt")
            setBody(request)
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun test500() = testApplication {
        val invoiceRepository = mockk<InvoiceRepository>()
        every { invoiceRepository.save(any()) } throws RuntimeException("Test error")
        mockkObject(DIContainer)
        every { DIContainer.invoiceRepository() } returns invoiceRepository

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val request = createRequest()

        val jwt = createTestJWT(
            mapOf("userId" to "0000004JFG398AF6GEKSVEQFZ4"),
            expireSeconds = 600,
        )

        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $jwt")
            setBody(request)
        }

        assertEquals(HttpStatusCode.InternalServerError, response.status)
        unmockkAll()
    }
}
