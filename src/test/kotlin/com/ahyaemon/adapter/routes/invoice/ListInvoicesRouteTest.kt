package com.ahyaemon.adapter.routes.invoice

import com.ahyaemon.adapter.DBTransactionManager
import com.ahyaemon.adapter.DIContainer
import com.ahyaemon.adapter.config.connectDB
import com.ahyaemon.adapter.config.createJWT
import com.ahyaemon.adapter.repositories.InvoiceDBRepository
import com.ahyaemon.application.invoice.InvoiceRepository
import com.ahyaemon.application.user.CompanyRepository
import com.ahyaemon.application.vo.Total
import com.ahyaemon.connectTestDB
import com.ahyaemon.domain.Billing
import com.ahyaemon.domain.Invoice
import com.ahyaemon.domain.vo.*
import com.ahyaemon.mockDB
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

import kotlin.test.Test
import kotlin.test.assertEquals

class ListInvoicesRouteTest {

    private fun createInvoice(
        invoiceId: String,
        paymentDueDate: String,
    ) = Invoice(
        id = InvoiceId.from(invoiceId),
        supplierId = SupplierId.from("0000004JFGVCGQFKE4JWFTZ8QK"),
        companyId = CompanyId.from("0000004JFG04KKT1AGGVYPFHP9"),
        createdUserId = UserId.from("0000004JFG398AF6GEKSVEQFZ4"),
        issuedDate = IssuedDate.from("2024-06-01"),
        paymentDueDate = PaymentDueDate.from(paymentDueDate),
        paymentAmount = PaymentAmount.from(10000),
        billing = Billing(
            amount = BillingAmount.from(10440),
            fee = Fee.from(400),
            feeRate = FeeRate.from("0.04"),
            consumptionTax = ConsumptionTax.from(40),
            consumptionTaxRate = ConsumptionTaxRate.from("0.1"),
        ),
        status = InvoiceStatus.UNTREATED,
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

    private fun setUpDB() {
        connectTestDB()
        val repository = InvoiceDBRepository()
        val invoices = listOf(
            createInvoice("0000004JFGZKB43Z12QBTY1YHB", "2024-06-01"),
            createInvoice("0000004JFGPW5E1PAEXN0XG24E", "2024-06-02"),
            createInvoice("0000004JFG1DYPVMFAMGR8XA5K", "2024-06-03"),
            createInvoice("0000004JFGGTTA0EPYNBGYXYRB", "2024-06-04"),
            createInvoice("0000004JFGE1PDZFY4FGXGWJRM", "2024-06-05"),
        )
        transaction {
            TransactionManager.current().exec("truncate table invoices")
            invoices.forEach {
                repository.save(it)
            }
        }
    }

    @Test
    fun test200() = testApplication {
        setUpDB()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val dateFrom = "2024-06-02"
        val dateTo = "2024-06-04"
        val jwt = createTestJWT(
            mapOf("userId" to "0000004JFG398AF6GEKSVEQFZ4"),
            expireSeconds = 600,
        )
        val url = "/invoices?dateFrom=$dateFrom&dateTo=$dateTo&limit=2&offset=1"
        val response = client.get(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $jwt")
        }

//        val listInvoicesResponse = response.body<ListInvoicesResponse>()
        print("-----")
        print(response.status)
//        assertEquals(HttpStatusCode.OK, response.status)
//        assertEquals(2, listInvoicesResponse.invoices.size)
//        assertEquals(3, listInvoicesResponse.total)
//        assertEquals("0000004JFGGTTA0EPYNBGYXYRB", listInvoicesResponse.invoices[0].invoiceId)
//        assertEquals("0000004JFGPW5E1PAEXN0XG24E", listInvoicesResponse.invoices[1].invoiceId)
    }

    @Test
    fun test400() = testApplication {
        val dateFrom = "2024-06-01aaa" // ここでエラー
        val dateTo = "2024-06-30"
        val jwt = createTestJWT(
            mapOf("userId" to "0000004JFGM4G8NJXH4QKH8QVJ"),
            expireSeconds = 600,
        )
        val url = "/invoices?dateFrom=$dateFrom&dateTo=$dateTo&limit=0&offset=0"
        val response = client.get(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $jwt")
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun test400_DateFromAfterDateTo() = testApplication {
        val dateFrom = "2024-06-30"
        val dateTo = "2024-06-01"
        val jwt = createTestJWT(
            mapOf("userId" to "0000004JFGM4G8NJXH4QKH8QVJ"),
            expireSeconds = 600,
        )
        val url = "/invoices?dateFrom=$dateFrom&dateTo=$dateTo&limit=0&offset=0"
        val response = client.get(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $jwt")
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun test401_expired() = testApplication {
        val dateFrom = "2024-06-30"
        val dateTo = "2024-06-01"
        val jwt = createTestJWT(
            mapOf("userId" to "0000004JFGM4G8NJXH4QKH8QVJ"),
            expireSeconds = 0,
        )
        val url = "/invoices?dateFrom=$dateFrom&dateTo=$dateTo&limit=0&offset=0"
        val response = client.get(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $jwt")
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }
}
