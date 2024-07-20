package com.ahyaemon.domain

import com.ahyaemon.domain.vo.*

/**
 * 請求書。
 */
data class Invoice (
    val id: InvoiceId,
    val supplierId: SupplierId,
    val companyId: CompanyId,
    val createdUserId: UserId,
    val issuedDate: IssuedDate,
    val paymentDueDate: PaymentDueDate,
    val paymentAmount: PaymentAmount,
    val billing: Billing,
    val status: InvoiceStatus,
) {

    companion object {

        fun from(
            invoiceId: InvoiceId,
            supplierId: SupplierId,
            companyId: CompanyId,
            createdUserId: UserId,
            issuedDate: IssuedDate,
            paymentDueDate: PaymentDueDate,
            paymentAmount: PaymentAmount,
            status: InvoiceStatus,
            feeRate: FeeRate,
            consumptionTaxRate: ConsumptionTaxRate,
        ): Invoice {
            val billing = Billing.from(
                paymentAmount,
                feeRate,
                consumptionTaxRate,
            )

            return Invoice(
                invoiceId,
                supplierId,
                companyId,
                createdUserId,
                issuedDate,
                paymentDueDate,
                paymentAmount,
                billing,
                status,
            )
        }
    }
}
