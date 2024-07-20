package com.ahyaemon.application.invoice

import com.ahyaemon.application.vo.Limit
import com.ahyaemon.application.vo.Offset
import com.ahyaemon.application.vo.Total
import com.ahyaemon.domain.Invoice
import com.ahyaemon.domain.vo.PaymentDueDate
import com.ahyaemon.domain.vo.UserId

/**
 * 請求書（Invoice）に関するリポジトリ。
 */
interface InvoiceRepository {

    /**
     * 請求書を保存する。
     * @param invoice Invoice
     */
    fun save(invoice: Invoice)

    /**
     * 条件にマッチする請求書のリストを取得する。
     * @param createdUserId 作成したユーザーID
     * @param dateFrom 請求日の from
     * @param dateTo 請求日の to
     * @param limit 取得件数
     * @param offset offset
     * @return 請求書のリスト
     */
    fun list(
        createdUserId: UserId,
        dateFrom: PaymentDueDate,
        dateTo: PaymentDueDate,
        limit: Limit,
        offset: Offset,
    ): List<Invoice>

    /**
     * 条件にマッチする請求書の件数を取得する。
     * @param createdUserId 作成したユーザーID
     * @param dateFrom 請求日の from
     * @param dateTo 請求日の to
     * @return トータル件数
     */
    fun total(
        createdUserId: UserId,
        dateFrom: PaymentDueDate,
        dateTo: PaymentDueDate,
    ): Total
}
