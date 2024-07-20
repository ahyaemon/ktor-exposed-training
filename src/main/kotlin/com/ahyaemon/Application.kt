package com.ahyaemon

import com.ahyaemon.adapter.config.configureSecurity
import com.ahyaemon.adapter.config.connectDB
import com.ahyaemon.adapter.config.migrateDB
import com.ahyaemon.adapter.routes.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    connectDB()
    migrateDB()
    // NOTE security の設定は routing の設定よりも前にないとダメ
    // https://stackoverflow.com/questions/71026232/how-do-you-configure-ktor-to-enable-jwt-authentication
    configureSecurity()
    configureRouting()
}
