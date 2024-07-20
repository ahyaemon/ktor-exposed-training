package com.ahyaemon.adapter.routes

import com.ahyaemon.adapter.routes.hello.configureHelloWorldAuthRouting
import com.ahyaemon.adapter.routes.hello.configureHelloWorldRouting
import com.ahyaemon.adapter.routes.invoice.CreateInvoiceRequest
import com.ahyaemon.adapter.routes.invoice.createInvoiceRouting
import com.ahyaemon.adapter.routes.invoice.createInvoiceValidates
import com.ahyaemon.adapter.routes.invoice.listInvoiceRouting
import com.ahyaemon.adapter.routes.user.*
import com.ahyaemon.application.LoginFailedException
import com.ahyaemon.application.NotFoundException
import com.ahyaemon.application.UserAlreadyExistsException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

/**
 * 下記を設定するための Ktor 拡張関数。
 * - リクエストバリデーション
 * - エラーハンドリング
 * - ルーティング
 *
 * 各ルーティングは一度ここにまとめておくことで、Application.kt の見通しを良くする
 */
fun Application.configureRouting() {

    // リクエストバリデーション
    install(RequestValidation) {
        for (f in createInvoiceValidates) {
            validate<CreateInvoiceRequest>(f)
        }

        for (f in createUserValidates) {
            validate<CreateUserRequest>(f)
        }

        for (f in loginValidates) {
            validate<LoginRequest>(f)
        }
    }

    // エラーハンドリング
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            val errorResponse = ValidationErrorResponse(messages = cause.reasons)
            call.respond(HttpStatusCode.BadRequest, errorResponse)
        }
        exception<NotFoundException> { call, cause ->
            val errorResponse = ErrorResponse(message = cause.message.toString())
            call.respond(HttpStatusCode.NotFound, errorResponse)
        }
        exception<LoginFailedException> { call, cause ->
            val errorResponse = ErrorResponse(message = cause.message.toString())
            call.respond(HttpStatusCode.Unauthorized, errorResponse)
        }
        exception<UserAlreadyExistsException> { call, cause ->
            val errorResponse = ErrorResponse(message = cause.message.toString())
            call.respond(HttpStatusCode.Conflict, errorResponse)
        }
        exception<Throwable> { call, cause ->
            call.application.log.error(cause.message, cause)
            val errorResponse = ErrorResponse(message = "Internal Server Error")
            call.respond(HttpStatusCode.InternalServerError, errorResponse)
        }
    }

    // ルーティング
    configureHelloWorldRouting()
    configureHelloWorldAuthRouting()

    createInvoiceRouting()
    listInvoiceRouting()

    createUserRouting()
    loginRouting()
}
