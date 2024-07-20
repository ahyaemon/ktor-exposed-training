package com.ahyaemon.adapter.db.tables

import com.ahyaemon.domain.Billing
import com.ahyaemon.domain.Invoice
import com.ahyaemon.domain.vo.*
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import java.math.BigDecimal

/**
 * invoices テーブル
 */
object InvoicesTable : Table() {
    val id: Column<String> = text("id")
    val companyId: Column<String> = (text("company_id") references CompaniesTable.id)
    val supplierId: Column<String> = text("supplier_id")
    val createdUserId: Column<String> = text("created_user_id")
    val issuedDate: Column<LocalDate> = date("issued_date")
    val paymentAmount: Column<Int> = integer("payment_amount")
    val fee: Column<Int> = integer("fee")
    val feeRate: Column<BigDecimal> = decimal("fee_rate", 3, 2)
    val consumptionTax: Column<Int> = integer("consumption_tax")
    val consumptionTaxRate: Column<BigDecimal> = decimal("consumption_tax_rate", 3, 2)
    val billingAmount: Column<Int> = integer("billing_amount")
    val paymentDueDate: Column<LocalDate> = date("payment_due_date")
    val status: Column<String> = text("status")

    override val primaryKey = PrimaryKey(id)

    fun toInvoice(row: ResultRow) = Invoice(
        id = InvoiceId.from(row[id]),
        supplierId = SupplierId.from(row[supplierId]),
        companyId = CompanyId.from(row[companyId]),
        createdUserId = UserId.from(row[createdUserId]),
        issuedDate = IssuedDate(row[issuedDate]),
        paymentDueDate = PaymentDueDate(row[paymentDueDate]),
        paymentAmount = PaymentAmount.from(row[paymentAmount]),
        billing = Billing(
            amount = BillingAmount(row[billingAmount]),
            fee = Fee.from(row[fee]),
            feeRate = FeeRate(row[feeRate]),
            consumptionTax = ConsumptionTax.from(row[consumptionTax]),
            consumptionTaxRate = ConsumptionTaxRate(row[consumptionTaxRate]),
        ),
        status = InvoiceStatus.valueOf(row[status]),
    )
}
