package com.ahyaemon.application.invoice

import com.ahyaemon.application.TransactionManager
import com.ahyaemon.application.UserNotFoundException
import com.ahyaemon.application.user.CompanyRepository
import com.ahyaemon.domain.Invoice
import com.ahyaemon.domain.vo.*

/**
 * 請求書作成ユースケース。
 */
class CreateInvoiceUseCase(
    private val invoiceRepository: InvoiceRepository,
    private val companyRepository: CompanyRepository,
    private val tx: TransactionManager,
) {

    /**
     * 請求書を作成する。
     * @return 作成した請求書
     * @throws UserNotFoundException ユーザーが見つからない場合
     */
    fun handle(
        createdUserId: UserId,
        supplierId: SupplierId,
        issuedDate: IssuedDate,
        paymentDueDate: PaymentDueDate,
        paymentAmount: PaymentAmount,
    ): Invoice = tx.begin {
        val invoiceId = InvoiceId.random()

        val user = companyRepository.selectUser(createdUserId)
            ?: throw UserNotFoundException("User not found. userId: $createdUserId.")
        val companyId = user.companyId

        // TODO Rate 系は DB 等で日付区間と共に管理する
        //   現在日時における Rate で計算するようにする
        val feeRate = FeeRate.from("0.04")
        val consumptionTaxRate = ConsumptionTaxRate.from("0.1")

        val invoice = Invoice.from(
            invoiceId,
            supplierId,
            companyId,
            createdUserId,
            issuedDate,
            paymentDueDate,
            paymentAmount,
            InvoiceStatus.UNTREATED,
            feeRate,
            consumptionTaxRate,
        )
        invoiceRepository.save(invoice)

        invoice
    }
}
