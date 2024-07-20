package com.ahyaemon

import org.jetbrains.exposed.sql.Database

/**
 * テスト用データベースに接続する。
 * TODO Docker 起動時にも DB にアクセスできるようにする。
 */
fun connectTestDB() {
    Database.connect(
        url = "jdbc:postgresql://localhost:15432/postgres",
        user = "postgres",
        password = "postgres",
    )
}