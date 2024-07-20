package com.ahyaemon.application.user

import com.ahyaemon.application.LoginFailedException
import com.ahyaemon.application.TransactionManager
import com.ahyaemon.application.UserNotFoundException
import com.ahyaemon.domain.BCryptHashProvider
import com.ahyaemon.domain.User
import com.ahyaemon.domain.vo.*

/**
 * ログインユースケース
 */
class LoginUseCase(
    private val companyRepository: CompanyRepository,
    private val tx: TransactionManager,
) {

    /**
     * ログイン処理（ユーザーのパスワード突合）を実施する。
     * @return パスワードが正しい場合 User を返す
     * @throws UserNotFoundException ユーザーが存在しない場合
     * @throws LoginFailedException パスワードの突合に失敗した場合
     */
    fun handle(
        mailAddress: MailAddress,
        rawPassword: RawPassword,
    ): User = tx.begin {
        val user = companyRepository.selectUserByMailAddress(mailAddress)
            ?: throw UserNotFoundException("User not found.")

        if (!BCryptHashProvider.isValid(rawPassword.value, user.passwordHash.value)) {
            throw LoginFailedException("Authentication failed. Password may be wrong.")
        }

        user
    }
}
