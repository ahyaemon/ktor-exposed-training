package com.ahyaemon.adapter.repositories

import com.ahyaemon.adapter.db.tables.CompaniesTable
import com.ahyaemon.adapter.db.tables.UsersTable
import com.ahyaemon.application.user.CompanyRepository
import com.ahyaemon.domain.Company
import com.ahyaemon.domain.User
import com.ahyaemon.domain.vo.MailAddress
import com.ahyaemon.domain.vo.UserId
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

/**
 * 会社 (Company) や ユーザー (User) に関して DB とやりとりする。
 */
class CompanyDBRepository : CompanyRepository {

    override fun save(company: Company) {
        CompaniesTable.insert {
            it[id] = company.id.value
            it[corporateName] = company.corporateName.value
            it[representativeName] = company.representativeName.value
            it[phoneNumber] = company.phoneNumber.value
            it[postCode] = company.postCode.value
            it[address] = company.address.value
        }

        for (user in company.users) {
            UsersTable.insert {
                it[id] = user.id.value
                it[companyId] = company.id.value
                it[name] = user.name.value
                it[mailAddress] = user.mailAddress.value
                it[password] = user.passwordHash.value
            }
        }
    }

    override fun selectUser(userId: UserId): User? {
        return UsersTable
            .selectAll()
            .where {
                UsersTable.id.eq(userId.value)
            }
            .map { UsersTable.toUser(it) }
            .firstOrNull()
    }

    override fun selectUserByMailAddress(mailAddress: MailAddress): User? {
        return UsersTable
            .selectAll()
            .where {
                UsersTable.mailAddress.eq(mailAddress.value)
            }
            .map { UsersTable.toUser(it) }
            .firstOrNull()
    }
}
