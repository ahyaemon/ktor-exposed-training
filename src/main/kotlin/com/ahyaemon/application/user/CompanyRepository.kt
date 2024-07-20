package com.ahyaemon.application.user

import com.ahyaemon.domain.Company
import com.ahyaemon.domain.User
import com.ahyaemon.domain.vo.MailAddress
import com.ahyaemon.domain.vo.UserId

/**
 * Company, User に関するリポジトリ。
 */
interface CompanyRepository {

    /**
     * Company とそれに紐づく User を保存する。
     * @param company Company
     */
    fun save(company: Company)

    /**
     * ユーザー から User を取得する。
     * @param userId: ユーザーID
     * @return User(見つからない場合は null)
     */
    fun selectUser(userId: UserId): User?

    /**
     * メールアドレスから User を取得する。
     * @param mailAddress メールアドレス
     * @return User(見つからない場合は null)
     */
    fun selectUserByMailAddress(mailAddress: MailAddress): User?
}
