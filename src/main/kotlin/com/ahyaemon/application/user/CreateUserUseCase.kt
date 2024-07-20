package com.ahyaemon.application.user

import com.ahyaemon.application.TransactionManager
import com.ahyaemon.application.UserAlreadyExistsException
import com.ahyaemon.domain.Company
import com.ahyaemon.domain.User
import com.ahyaemon.domain.vo.*

/**
 * ユーザー作成ユースケース。
 */
class CreateUserUseCase(
    private val companyRepository: CompanyRepository,
    private val tx: TransactionManager,
) {

    /**
     * ユーザーを作成する。
     * @return 作成した企業、ユーザー
     * @throws UserAlreadyExistsException メールアドレスが既に使われている場合
     */
    fun handle(
        corporateName: CorporateName,
        representativeName: RepresentativeName,
        phoneNumber: PhoneNumber,
        postCode: PostCode,
        address: Address,
        userName: UserName,
        mailAddress: MailAddress,
        rawPassword: RawPassword,
    ): Company = tx.begin {
        val isUserExists = companyRepository.selectUserByMailAddress(mailAddress) != null
        if (isUserExists) {
            throw UserAlreadyExistsException("User already exists. email: ${mailAddress.value}")
        }

        val passwordHash = PasswordHash.from(rawPassword)

        val userId = UserId.random()
        val companyId = CompanyId.random()

        val user = User.from(
            id = userId,
            companyId = companyId,
            name = userName,
            mailAddress = mailAddress,
            passwordHash = passwordHash,
        )

        val company = Company(
            id = companyId,
            users = listOf(user),
            corporateName = corporateName,
            representativeName = representativeName,
            phoneNumber = phoneNumber,
            postCode = postCode,
            address = address,
        )

        companyRepository.save(company)

        company
    }
}
