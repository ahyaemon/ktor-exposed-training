package com.ahyaemon.application.invoice

import com.ahyaemon.application.TransactionManager
import com.ahyaemon.application.vo.Limit
import com.ahyaemon.application.vo.Offset
import com.ahyaemon.application.vo.Total
import com.ahyaemon.domain.Invoice
import com.ahyaemon.domain.vo.*

/**
 * 請求書一覧取得ユースケース。
 */
class ListInvoicesUseCase(
    private val invoiceRepository: InvoiceRepository,
    private val tx: TransactionManager,
) {

    /**
     * 請求書の一覧を取得する。
     * @return Pair<請求書のリスト, トータル件数>
     */
    fun handle(
        createdUserId: UserId,
        dateFrom: PaymentDueDate,
        dateTo: PaymentDueDate,
        limit: Limit,
        offset: Offset,
    ): Pair<List<Invoice>, Total> = tx.begin {
        val total = invoiceRepository.total(createdUserId, dateFrom, dateTo)
        val invoices = invoiceRepository.list(createdUserId, dateFrom, dateTo, limit, offset)

        (invoices to total)
    }
}
