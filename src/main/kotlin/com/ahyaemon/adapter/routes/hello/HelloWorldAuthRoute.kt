package com.ahyaemon.adapter.routes.hello

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * 認証後に hello, world を試すためのルーティング。
 * NOTE 本来は不要なルーティング
 */
fun Application.configureHelloWorldAuthRouting() {
    routing {
        authenticate("auth-jwt") {
            get("/auth") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $userId! Token is expired at $expiresAt ms.")
            }
        }
    }
}
