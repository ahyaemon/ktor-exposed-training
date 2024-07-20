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
import kotlin.test.assertTrue

class CreateUserRouteTest {

    private val url = "/users"

    private fun createRequest(
        corporateName: String = "テスト企業",
        representativeName: String = "代表テストマン",
        phoneNumber: String = "080-1234-5678",
        postCode: String = "000-1111",
        address: String = "沖縄",
        userName: String = "テストマン",
        mailAddress: String = "aaa@example.com",
        password: String = "password",
    ): CreateUserRequest {
        return CreateUserRequest(
            company = CreateUserRequest.Companion.CompanyRequest(
                corporateName,
                representativeName,
                phoneNumber,
                postCode,
                address,
            ),
            user = CreateUserRequest.Companion.UserRequest(
                userName,
                mailAddress,
                password,
            )
        )
    }

    @Test
    fun test200() = testApplication {
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
        val createUserResponse = response.body<CreateUserResponse>()

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue { ULIDProvider.isValid(createUserResponse.company.companyId) }
        assertEquals(request.company.corporateName, createUserResponse.company.corporateName)
        assertTrue { ULIDProvider.isValid(createUserResponse.user.userId) }
        assertEquals(request.user.name, createUserResponse.user.name)
    }

    @Test
    fun test400() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val request = createRequest(
            corporateName = "a".repeat(101),
            userName = "a".repeat(101),
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
    fun test409() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val request = createRequest(
            mailAddress = "test@example.com"
        )
        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.Conflict, response.status)
    }
}
