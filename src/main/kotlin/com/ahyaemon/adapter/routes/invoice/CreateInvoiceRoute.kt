package com.ahyaemon.adapter.routes.invoice

import com.ahyaemon.adapter.DIContainer
import com.ahyaemon.adapter.routes.createValidation
import com.ahyaemon.domain.Billing
import com.ahyaemon.domain.Invoice
import com.ahyaemon.domain.vo.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateInvoiceRequest(
    val supplierId: String,
    val issuedDate: String,
    val paymentDueDate: String,
    val paymentAmount: Int,
)

@Serializable
data class CreateInvoiceResponse(
    val invoiceId: String,
    val supplierId: String,
    val companyId: String,
    val createdUserId: String,
    val issuedDate: String,
    val paymentDueDate: String,
    val paymentAmount: Int,
    val billing: BillingResponse,
    val status: String,
) {


    companion object {

        @Serializable
        data class BillingResponse(
            val amount: Int,
            val fee: Int,
            val feeRate: String,
            val consumptionTax: Int,
            val consumptionTaxRate: String,
        ) {

            companion object {

                fun from(billing: Billing): BillingResponse {
                    return BillingResponse(
                        amount = billing.amount.value,
                        fee = billing.fee.value,
                        feeRate = billing.feeRate.value.toString(),
                        consumptionTax = billing.consumptionTax.value,
                        consumptionTaxRate = billing.consumptionTaxRate.value.toString(),
                    )
                }
            }
        }

        fun from(invoice: Invoice): CreateInvoiceResponse {
            return CreateInvoiceResponse(
                invoiceId = invoice.id.value,
                supplierId = invoice.supplierId.value,
                companyId = invoice.companyId.value,
                createdUserId = invoice.createdUserId.value,
                issuedDate = invoice.issuedDate.value.toString(),
                paymentDueDate = invoice.paymentDueDate.value.toString(),
                paymentAmount = invoice.paymentAmount.value,
                billing = BillingResponse.from(invoice.billing),
                status = invoice.status.name,
            )
        }
    }
}

val createInvoiceValidates = listOf(
    createValidation<CreateInvoiceRequest> { SupplierId.from(it.supplierId) },
    createValidation { IssuedDate.from(it.issuedDate) },
    createValidation { PaymentDueDate.from(it.paymentDueDate) },
    createValidation { PaymentAmount.from(it.paymentAmount) },
)

/**
 * 請求書作成のルーティング。
 */
fun Application.createInvoiceRouting() {

    val useCase = DIContainer.createInvoiceUseCase()

    routing {
        authenticate("auth-jwt") {
            post("/invoices") {
                val request = call.receive<CreateInvoiceRequest>()

                val principal = call.principal<JWTPrincipal>()
                val userId = UserId.from(principal!!.payload.getClaim("userId").asString())

                val supplierId = SupplierId.from(request.supplierId)
                val issuedDate = IssuedDate.from(request.issuedDate)
                val paymentDueDate = PaymentDueDate.from(request.paymentDueDate)
                val paymentAmount = PaymentAmount.from(request.paymentAmount)

                val invoice = useCase.handle(
                    userId,
                    supplierId,
                    issuedDate,
                    paymentDueDate,
                    paymentAmount,
                )

                val response = CreateInvoiceResponse.from(invoice)
                call.respond(response)
            }
        }
    }
}
