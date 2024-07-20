package com.ahyaemon.adapter.routes.user

import com.ahyaemon.adapter.DIContainer
import com.ahyaemon.adapter.routes.createValidation
import com.ahyaemon.domain.Company
import com.ahyaemon.domain.User
import com.ahyaemon.domain.vo.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val company: CompanyRequest,
    val user: UserRequest,
) {

    companion object {

        @Serializable
        data class CompanyRequest(
            val corporateName: String,
            val representativeName: String,
            val phoneNumber: String,
            val postCode: String,
            val address: String,
        )

        @Serializable
        data class UserRequest(
            val name: String,
            val mailAddress: String,
            val password: String,
        )
    }
}

@Serializable
data class CreateUserResponse(
    val company: CompanyResponse,
    val user: UserResponse,
) {
    companion object {

        @Serializable
        data class CompanyResponse(
            val companyId: String,
            val corporateName: String,
            val representativeName: String,
            val phoneNumber: String,
            val postCode: String,
            val address: String,
        ) {

            companion object {

                fun from(company: Company): CompanyResponse {
                    return CompanyResponse(
                        companyId = company.id.value,
                        corporateName = company.corporateName.value,
                        representativeName = company.representativeName.value,
                        phoneNumber = company.phoneNumber.value,
                        postCode = company.postCode.value,
                        address = company.address.value,
                    )
                }
            }
        }

        @Serializable
        data class UserResponse(
            val userId: String,
            val name: String,
            val mailAddress: String,
        ) {

            companion object {

                fun from(user: User): UserResponse {
                    return UserResponse(
                        userId = user.id.value,
                        name = user.name.value,
                        mailAddress = user.mailAddress.value,
                    )
                }
            }
        }

        fun from(company: Company): CreateUserResponse {
            return CreateUserResponse(
                company = CompanyResponse.from(company),
                user = UserResponse.from(company.users.first())
            )
        }
    }
}

val createUserValidates = listOf(
    createValidation<CreateUserRequest> { CorporateName.from(it.company.corporateName) },
    createValidation { RepresentativeName.from(it.company.representativeName) },
    createValidation { PhoneNumber.from(it.company.phoneNumber) },
    createValidation { PostCode.from(it.company.postCode) },
    createValidation { Address.from(it.company.address) },
    createValidation { UserName.from(it.user.name) },
    createValidation { MailAddress.from(it.user.mailAddress) },
    createValidation { RawPassword.from(it.user.password) }
)

/**
 * ユーザー作成のルーティング。
 */
fun Application.createUserRouting() {

    val useCase = DIContainer.createUserUseCase()

    routing {
        post("/users") {
            val body = call.receive<CreateUserRequest>()

            val corporateName = CorporateName.from(body.company.corporateName)
            val representativeName = RepresentativeName.from(body.company.representativeName)
            val phoneNumber = PhoneNumber.from(body.company.phoneNumber)
            val postCode = PostCode.from(body.company.postCode)
            val address = Address.from(body.company.address)
            val userName = UserName.from(body.user.name)
            val mailAddress = MailAddress.from(body.user.mailAddress)
            val password = RawPassword.from(body.user.password)

            val company = useCase.handle(
                corporateName,
                representativeName,
                phoneNumber,
                postCode,
                address,
                userName,
                mailAddress,
                password,
            )

            call.respond(CreateUserResponse.from(company))
        }
    }
}
