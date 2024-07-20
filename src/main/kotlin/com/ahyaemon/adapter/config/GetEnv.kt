package com.ahyaemon.adapter.config

import io.ktor.server.application.Application

/**
 * 環境変数を取得する。
 * @param key: 取得するキー
 */
fun Application.getEnv(key: String): String = environment
    .config
    .property(key)
    .getString()
