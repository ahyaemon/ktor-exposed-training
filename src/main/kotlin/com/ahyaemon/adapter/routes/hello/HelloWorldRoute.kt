package com.ahyaemon.adapter.routes.hello

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * hello, world を試すためのルーティング。
 * NOTE 本来は不要なルーティング
 */
fun Application.configureHelloWorldRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
