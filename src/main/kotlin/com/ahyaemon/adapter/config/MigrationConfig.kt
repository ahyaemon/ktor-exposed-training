package com.ahyaemon.adapter.config

import io.ktor.server.application.*
import org.flywaydb.core.Flyway

object MigrationConfig {

    /**
     * DB マイグレーションを実行する。
     * @param url DB 接続 URL
     * @param user DB 接続ユーザー
     * @param password DB 接続パスワード
     */
    fun configure(
        url: String,
        user: String,
        password: String,
    ) {
        val flyway =
            Flyway.configure()
                .dataSource(url, user, password)
                .load()
        flyway.migrate()
    }
}

/**
 * DB マイグレーションを実行するための Ktor 拡張関数。
 */
fun Application.migrateDB() {
    val url = getEnv("db.url")
    val user = getEnv("db.user")
    val password = getEnv("db.password")
    MigrationConfig.configure(url, user, password)
}
