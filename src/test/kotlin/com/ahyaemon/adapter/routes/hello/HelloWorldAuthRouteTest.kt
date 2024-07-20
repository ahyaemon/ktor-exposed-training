package com.ahyaemon.adapter.routes.hello

import com.ahyaemon.adapter.config.createJWT
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class HelloWorldAuthRouteTest {

    private val url = "/auth"

    private fun createTestJWT(
        claims: Map<String, String>,
        expireSeconds: Int,
    ): String {
        val secret = "secret"
        val issuer = "http://0.0.0.0:18080"
        val audience = "http://0.0.0.0:18080"

        return createJWT(
            secret = secret,
            issuer = issuer,
            audience = audience,
            claims = claims,
            expireSeconds = expireSeconds,
        )
    }

    @Test
    fun test200() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val jwt = createTestJWT(
            claims = mapOf("userId" to "0000004JFG5ZWVABZJP3Y9XVWR"),
            expireSeconds = 60,
        )

        val response = client.get(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $jwt")
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun test401_expire() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val jwt = createTestJWT(
            claims = mapOf("userId" to "0000004JFG5ZWVABZJP3Y9XVWR"),
            expireSeconds = 0,
        )

        val response = client.get(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $jwt")
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun test401_invalid_format() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val jwt = createTestJWT(
            claims = mapOf("userId" to "0000004JFG5ZWVABZJP3Y9XVWR"),
            expireSeconds = 0,
        )

        val response = client.get(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer AAA")
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun test401_no_userId() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val jwt = createTestJWT(
            claims = mapOf("a" to "b"),
            expireSeconds = 60,
        )

        val response = client.get(url) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $jwt")
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }
}