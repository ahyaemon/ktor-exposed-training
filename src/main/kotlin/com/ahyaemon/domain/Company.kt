package com.ahyaemon.domain

import com.ahyaemon.domain.vo.*

/**
 * 企業。
 */
data class Company(
    val id: CompanyId,
    val users: List<User>,
    val corporateName: CorporateName,
    val representativeName: RepresentativeName,
    val phoneNumber: PhoneNumber,
    val postCode: PostCode,
    val address: Address,
) {

    companion object {

        fun from(
            id: CompanyId,
            users: List<User>,
            corporateName: CorporateName,
            representativeName: RepresentativeName,
            phoneNumber: PhoneNumber,
            postCode: PostCode,
            address: Address,
        ): Company {
            return Company (
                id = id,
                users = users,
                corporateName = corporateName,
                representativeName = representativeName,
                phoneNumber = phoneNumber,
                postCode = postCode,
                address = address,
            )
        }
    }
}
