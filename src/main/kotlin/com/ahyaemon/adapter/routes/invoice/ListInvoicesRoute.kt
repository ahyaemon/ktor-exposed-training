package com.ahyaemon.adapter.routes.invoice

import com.ahyaemon.adapter.DIContainer
import com.ahyaemon.adapter.routes.ErrorResponse
import com.ahyaemon.application.vo.Limit
import com.ahyaemon.application.vo.Offset
import com.ahyaemon.application.vo.Total
import com.ahyaemon.domain.Billing
import com.ahyaemon.domain.Invoice
import com.ahyaemon.domain.vo.PaymentDueDate
import com.ahyaemon.domain.vo.UserId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ListInvoicesRequest(
    val dateFrom: String,
    val dateTo: String,
)

@Serializable
data class ListInvoicesResponse(
    val invoices: List<InvoiceResponse>,
    val total: Long,
) {

    companion object {

        // NOTE 名前衝突回避のために内部クラスにする
        //   他のルーティングと共通化もできるが、将来的な変更があった時に影響範囲が広くなるため今回は分ける
        //   レスポンスで返す json のネストに合わせて class のネストも深くなるのが難点
        @Serializable
        data class InvoiceResponse(
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

                fun from(invoice: Invoice): InvoiceResponse {
                    return InvoiceResponse(
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

        fun from(
            invoices: List<Invoice>,
            total: Total,
        ) = ListInvoicesResponse(
            invoices = invoices.map { InvoiceResponse.from(it) },
            total = total.value,
        )
    }
}

/**
 * 請求書一覧取得のルーティング。
 */
fun Application.listInvoiceRouting() {
    val useCase = DIContainer.listInvoiceUseCase()

    routing {
        authenticate("auth-jwt") {
            get("/invoices") {
                // NOTE Location を使っても良さそう
                //    https://ktor.io/docs/server-locations.html#route-handlers
                val dateFrom = try {
                    PaymentDueDate.from(call.parameters["dateFrom"])
                } catch (e: IllegalArgumentException) {
                    call.response.status(HttpStatusCode.BadRequest)
                    call.respond(ErrorResponse(message = "dateFrom: " + e.localizedMessage))
                    return@get
                }
                val dateTo = try {
                    PaymentDueDate.from(call.parameters["dateTo"])
                } catch (e: IllegalArgumentException) {
                    call.response.status(HttpStatusCode.BadRequest)
                    call.respond(ErrorResponse(message = "dateTo: " + e.localizedMessage))
                    return@get
                }

                if (dateTo.isAfter(dateFrom)) {
                    call.response.status(HttpStatusCode.BadRequest)
                    call.respond(ErrorResponse(message = "dateTo(${dateTo.value}) must be after the dateFrom(${dateFrom.value})"))
                    return@get
                }

                val limit = try {
                    Limit.from(call.parameters["limit"])
                } catch (e: IllegalArgumentException) {
                    call.response.status(HttpStatusCode.BadRequest)
                    call.respond(ErrorResponse(message = e.localizedMessage))
                    return@get
                }
                val offset = try {
                    Offset.from(call.parameters["offset"])
                } catch (e: IllegalArgumentException) {
                    call.response.status(HttpStatusCode.BadRequest)
                    call.respond(ErrorResponse(message = e.localizedMessage))
                    return@get
                }

                val principal = call.principal<JWTPrincipal>()
                val userId = UserId.from(principal!!.payload.getClaim("userId").asString())

                val (invoices, total) = useCase.handle(
                    createdUserId = userId,
                    dateFrom = dateFrom,
                    dateTo = dateTo,
                    limit = limit,
                    offset = offset,
                )

                val response = ListInvoicesResponse.from(invoices, total)
                call.respond(response)
            }
        }
    }
}
