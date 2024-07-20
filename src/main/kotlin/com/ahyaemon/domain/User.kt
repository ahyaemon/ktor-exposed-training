package com.ahyaemon.domain

import com.ahyaemon.domain.vo.*

/**
 * ユーザー。
 */
data class User(
    val id: UserId,
    val companyId: CompanyId,
    val name: UserName,
    val mailAddress: MailAddress,
    val passwordHash: PasswordHash,
) {

    companion object {

        fun from(
            id: UserId,
            companyId: CompanyId,
            name: UserName,
            mailAddress: MailAddress,
            passwordHash: PasswordHash,
        ): User {
            return User(
                id = id,
                companyId = companyId,
                name = name,
                mailAddress = mailAddress,
                passwordHash = passwordHash,
            )
        }
    }
}
