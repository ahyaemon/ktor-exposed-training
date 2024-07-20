package com.ahyaemon.adapter.db.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * companies テーブル
 */
object CompaniesTable : Table() {
    val id: Column<String> = text("id")
    val corporateName: Column<String> = text("corporate_name")
    val representativeName: Column<String> = text("representative_name")
    val phoneNumber: Column<String> = text("phone_number")
    val postCode: Column<String> = text("post_code")
    val address: Column<String> = text("address")

    override val primaryKey = PrimaryKey(id)
}
