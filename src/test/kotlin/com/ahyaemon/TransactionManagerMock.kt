package com.ahyaemon

import com.ahyaemon.application.TransactionManager

class TransactionManagerMock: TransactionManager {

    override fun <T> begin(f: () -> T): T = f()
}