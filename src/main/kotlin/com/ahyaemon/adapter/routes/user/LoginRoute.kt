package com.ahyaemon.adapter.routes.user

import com.ahyaemon.adapter.DIContainer
import com.ahyaemon.adapter.config.createJWT
import com.ahyaemon.adapter.routes.createValidation
import com.ahyaemon.domain.vo.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val mailAddress: String,
    val password: String,
)

@Serializable
data class LoginResponse(
    val token: String,
)

val loginValidates = listOf(
    createValidation<LoginRequest> { MailAddress.from(it.mailAddress) },
    createValidation { RawPassword.from(it.password) },
)

fun Application.getEnv(key: String): String = environment.config.property(key).getString()

/**
 * ログインのルーティング。
 */
fun Application.loginRouting() {

    routing {
        post("/users/login") {
            val useCase = DIContainer.loginUseCase()

            val body = call.receive<LoginRequest>()

            val mailAddress = MailAddress.from(body.mailAddress)
            val rawPassword = RawPassword.from(body.password)

            val user = useCase.handle(mailAddress, rawPassword)
            val token = context.application.createJWT(user)

            val response = LoginResponse(token = token)
            call.respond(response)
        }
    }
}
