package com.ahyaemon.adapter.repositories

import com.ahyaemon.adapter.db.tables.InvoicesTable
import com.ahyaemon.application.invoice.InvoiceRepository
import com.ahyaemon.application.vo.Limit
import com.ahyaemon.application.vo.Offset
import com.ahyaemon.application.vo.Total
import com.ahyaemon.domain.Invoice
import com.ahyaemon.domain.vo.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

/**
 * 請求書（Invoice）に関して DB とやりとりする
 */
class InvoiceDBRepository : InvoiceRepository {

    override fun save(invoice: Invoice) {
        InvoicesTable.insert {
            it[id] = invoice.id.value
            it[companyId] = invoice.companyId.value
            it[supplierId] = invoice.supplierId.value
            it[createdUserId] = invoice.createdUserId.value
            it[issuedDate] = invoice.issuedDate.value
            it[paymentAmount] = invoice.paymentAmount.value
            it[fee] = invoice.billing.fee.value
            it[feeRate] = invoice.billing.feeRate.value
            it[consumptionTax] = invoice.billing.consumptionTax.value
            it[consumptionTaxRate] = invoice.billing.consumptionTaxRate.value
            it[billingAmount] = invoice.billing.amount.value
            it[paymentDueDate] = invoice.paymentDueDate.value
            it[status] = invoice.status.name
        }
    }

    override fun list(
        createdUserId: UserId,
        dateFrom: PaymentDueDate,
        dateTo: PaymentDueDate,
        limit: Limit,
        offset: Offset,
    ): List<Invoice> {
        return InvoicesTable
            .selectAll()
            .where {
                InvoicesTable.createdUserId.eq(createdUserId.value) and
                InvoicesTable.paymentDueDate.greaterEq(dateFrom.value) and
                InvoicesTable.paymentDueDate.lessEq(dateTo.value)
            }
            .limit(limit.value, offset=offset.value.toLong())
            .orderBy(InvoicesTable.id)
            .map { InvoicesTable.toInvoice(it) }
    }

    override fun total(
        createdUserId: UserId,
        dateFrom: PaymentDueDate,
        dateTo: PaymentDueDate,
    ): Total {
        return InvoicesTable
            .select(InvoicesTable.id.count())
            .where {
                InvoicesTable.createdUserId.eq(createdUserId.value) and
                        InvoicesTable.paymentDueDate.greaterEq(dateFrom.value) and
                        InvoicesTable.paymentDueDate.lessEq(dateTo.value)
            }
            .map { Total.from(it[InvoicesTable.id.count()]) }
            .first()
    }
}
