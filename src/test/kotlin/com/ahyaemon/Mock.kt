package com.ahyaemon

import com.ahyaemon.adapter.DIContainer
import com.ahyaemon.adapter.config.DBConfig
import com.ahyaemon.adapter.config.MigrationConfig
import io.mockk.*

fun mockDB() {
    mockkObject(MigrationConfig)
    every { MigrationConfig.configure(any(), any(), any()) } just Runs

    mockkObject(DBConfig)
    every { DBConfig.configure(any(), any(), any()) } just Runs

    mockkObject(DIContainer)
    every { DIContainer.invoiceRepository() } returns mockk(relaxed = true)
    every { DIContainer.companyRepository() } returns mockk(relaxed = true)
    every { DIContainer.transactionManager() } returns TransactionManagerMock()
}
