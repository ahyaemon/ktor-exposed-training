package com.ahyaemon.adapter.routes.user

import com.ahyaemon.adapter.routes.ValidationErrorResponse
import com.ahyaemon.domain.ULIDProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*

import kotlin.test.Test
import kotlin.test.assertEquals

class LoginRouteTest {

    private val url = "/users/login"

    private fun createRequest(
        mailAddress: String = "test@example.com",
        password: String = "password",
    ): LoginRequest {
        return LoginRequest(
            mailAddress = mailAddress,
            password = password,
        )
    }

    @Test
    fun test200() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val request = createRequest()
        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun test400() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val request = createRequest(
            mailAddress = "a".repeat(101),
            password = "a".repeat(101),
        )

        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)

        val body = response.body<ValidationErrorResponse>()
        assertEquals(2, body.messages.size)
    }

    @Test
    fun test401() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val request = createRequest(
            password = ULIDProvider.generate()
        )

        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        println(response.body<String>())

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun test404() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val request = createRequest(
            mailAddress = ULIDProvider.generate() + "@example.com",
        )
        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }
}
