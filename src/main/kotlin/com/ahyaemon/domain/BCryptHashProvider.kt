package com.ahyaemon.domain

import org.mindrot.jbcrypt.BCrypt.*

object BCryptHashProvider {

    /**
     * BCrypt によりハッシュ化する。
     * @param ハッシュ化する文字列
     * @return ハッシュ化された文字列
     */
    fun generate(s: String): String {
        val salt = gensalt(4)
        val hashed = hashpw(s, salt)

        return hashed
    }

    /**
     * ハッシュ化前の文字列とハッシュ化後の文字列を突合する。
     * @param raw ハッシュ化前の文字列
     * @param hashed ハッシュ化後の文字列
     * @return 突合結果が正しい場合に true
     */
    fun isValid(raw: String, hashed: String): Boolean {
        return checkpw(raw, hashed)
    }
}
