package com.ahyaemon.adapter.db.tables

import com.ahyaemon.domain.User
import com.ahyaemon.domain.vo.*
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

/**
 * users テーブル
 */
object UsersTable : Table() {
    val id: Column<String> = text("id")
    val companyId: Column<String> = (text("company_id") references CompaniesTable.id)
    val name: Column<String> = text("name")
    val mailAddress: Column<String> = text("mail_address")
    val password: Column<String> = text("password")

    override val primaryKey = PrimaryKey(id)

    fun toUser(row: ResultRow) = User(
            id = UserId.from(row[id]),
            name = UserName.from(row[name]),
            companyId = CompanyId.from(row[companyId]),
            mailAddress = MailAddress.from(row[mailAddress]),
            passwordHash = PasswordHash(row[password]),
        )
}
