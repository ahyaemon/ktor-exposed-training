package com.ahyaemon.adapter

import com.ahyaemon.application.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * DB のトランザクションを管理する。
 */
class DBTransactionManager: TransactionManager {

    override fun <T> begin(f: () -> T): T = transaction { f() }
}
