package com.ahyaemon.adapter

import com.ahyaemon.adapter.repositories.CompanyDBRepository
import com.ahyaemon.adapter.repositories.InvoiceDBRepository
import com.ahyaemon.application.invoice.CreateInvoiceUseCase
import com.ahyaemon.application.invoice.InvoiceRepository
import com.ahyaemon.application.TransactionManager
import com.ahyaemon.application.invoice.ListInvoicesUseCase
import com.ahyaemon.application.user.CompanyRepository
import com.ahyaemon.application.user.CreateUserUseCase
import com.ahyaemon.application.user.LoginUseCase

/**
 * 簡易的な DI コンテナ。
  */
object DIContainer {

    fun transactionManager(): TransactionManager {
        return DBTransactionManager()
    }

    fun invoiceRepository(): InvoiceRepository {
        return InvoiceDBRepository()
    }

    fun companyRepository(): CompanyRepository {
        return CompanyDBRepository()
    }

    fun createInvoiceUseCase(): CreateInvoiceUseCase {
        return CreateInvoiceUseCase(
            invoiceRepository(),
            companyRepository(),
            transactionManager(),
        )
    }

    fun listInvoiceUseCase(): ListInvoicesUseCase {
        return ListInvoicesUseCase(
            invoiceRepository(),
            transactionManager(),
        )
    }

    fun createUserUseCase(): CreateUserUseCase {
        return CreateUserUseCase(
            companyRepository(),
            transactionManager(),
        )
    }

    fun loginUseCase(): LoginUseCase {
        return LoginUseCase(
            companyRepository(),
            transactionManager(),
        )
    }
}
