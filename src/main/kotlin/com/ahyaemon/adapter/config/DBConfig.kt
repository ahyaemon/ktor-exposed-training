package com.ahyaemon.adapter.config

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

object DBConfig {

    /**
     * データベースに接続する。
     * @param url DB 接続 URL
     * @param user DB 接続ユーザー
     * @param password DB 接続パスワード
     */
    fun configure(
        url: String,
        user: String,
        password: String,
    ) {
        Database.connect(
            url = url,
            user = user,
            password = password,
        )
    }
}

/**
 * DB 接続用 Ktor 拡張関数。
 */
fun Application.connectDB() {
    val url = getEnv("db.url")
    val user = getEnv("db.user")
    val password = getEnv("db.password")
    DBConfig.configure(url, user, password)
}
