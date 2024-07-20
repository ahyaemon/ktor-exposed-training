package com.ahyaemon.adapter.config

import com.ahyaemon.adapter.routes.user.getEnv
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

/**
 * セキュリティを設定するための Ktor 拡張関数。
 * JWT による認証を設定する。
 * https://ktor.io/docs/server-jwt.html
 */
fun Application.configureSecurity() {
    val jwtVerifier = createJWTVerifier()
    val myRealm = getEnv("jwt.realm")

    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(jwtVerifier)

            validate { credential ->
                val userId = credential.payload.getClaim("userId").asString()
                if (userId.isNullOrEmpty()) {
                    null
                } else {
                    JWTPrincipal(credential.payload)
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
