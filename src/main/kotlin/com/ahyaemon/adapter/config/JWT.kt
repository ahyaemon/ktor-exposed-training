package com.ahyaemon.adapter.config

import com.ahyaemon.domain.User
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import java.util.*

/**
 * JWT を作成する。
 * @param secret
 * @param issuer
 * @param audience
 * @param claims
 * @param expireSeconds 期限切れになる秒数
 */
fun createJWT(
    secret: String,
    issuer: String,
    audience: String,
    claims: Map<String, String>,
    expireSeconds: Int,
): String {
    return JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .apply {
            claims.forEach { (key, value) ->
                withClaim(key, value)
            }
        }
        .withExpiresAt(Date(System.currentTimeMillis() + expireSeconds * 1000))
        .sign(Algorithm.HMAC256(secret))
}

/**
 * JWT を作成するための ktor 拡張関数。
 */
fun Application.createJWT(user: User): String {
    val secret = getEnv("jwt.secret")
    val issuer = getEnv("jwt.issuer")
    val audience = getEnv("jwt.audience")
    val claims = mapOf(
        "userId" to user.id.value,
    )

    return createJWT(
        secret = secret,
        issuer = issuer,
        audience = audience,
        claims = claims,
        expireSeconds = 600,
    )
}

/**
 * JWTVerifier を作成するための Ktor 拡張関数。
 */
fun Application.createJWTVerifier(): JWTVerifier {
    val secret = getEnv("jwt.secret")
    val issuer = getEnv("jwt.issuer")
    val audience = getEnv("jwt.audience")

    return JWT.require(Algorithm.HMAC256(secret))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()
}
