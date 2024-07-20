package com.ahyaemon.adapter.repositories

import com.ahyaemon.adapter.DBTransactionManager
import com.ahyaemon.adapter.config.connectDB
import com.ahyaemon.application.vo.Limit
import com.ahyaemon.application.vo.Offset
import com.ahyaemon.domain.Billing
import com.ahyaemon.domain.Invoice
import com.ahyaemon.domain.vo.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import kotlin.test.Test
import kotlin.test.assertEquals

class InvoiceDBRepositoryTest {

    private val repository = InvoiceDBRepository()
    private val tx = DBTransactionManager()

    private val createdUserId = UserId.from("0000004JFG398AF6GEKSVEQFZ4")

    private fun createInvoice(
        invoiceId: String,
        paymentDueDate: String,
    ) = Invoice(
        id = InvoiceId.from(invoiceId),
        supplierId = SupplierId.from("0000004JFGVCGQFKE4JWFTZ8QK"),
        companyId = CompanyId.from("0000004JFG04KKT1AGGVYPFHP9"),
        createdUserId = createdUserId,
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

    @Test
    fun testFromTo() = testApplication {
        application {
            connectDB()
            val invoice1 = createInvoice("0000004JFG1DYPVMFAMGR8XA5K", "2024-06-03")
            val invoice2 = createInvoice("0000004JFGE1PDZFY4FGXGWJRM", "2024-06-05")
            val invoice3 = createInvoice("0000004JFGGTTA0EPYNBGYXYRB", "2024-06-04")
            val invoice4 = createInvoice("0000004JFGPW5E1PAEXN0XG24E", "2024-06-02")
            val invoice5 = createInvoice("0000004JFGZKB43Z12QBTY1YHB", "2024-06-01")
            tx.begin {
                TransactionManager.current().exec("truncate table invoices")
                repository.save(invoice1)
                repository.save(invoice2)
                repository.save(invoice3)
                repository.save(invoice4)
                repository.save(invoice5)
                val invoiceResults = repository.list(
                    createdUserId = createdUserId,
                    dateFrom = PaymentDueDate.from("2024-06-02"),
                    dateTo = PaymentDueDate.from("2024-06-04"),
                    limit = Limit(2),
                    offset = Offset(1),
                )
                val total = repository.total(
                    createdUserId = createdUserId,
                    dateFrom = PaymentDueDate.from("2024-06-02"),
                    dateTo = PaymentDueDate.from("2024-06-04"),
                )
                println(invoiceResults[0].id.value)
                println(invoiceResults[1].id.value)

                assertEquals(invoice3.id, invoiceResults[0].id)
                assertEquals(invoice4.id, invoiceResults[1].id)
                assertEquals(3, total.value)
            }
        }
    }
}
