package com.ahyaemon.adapter.routes

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
)

@Serializable
data class ValidationErrorResponse(
    val messages: List<String>,
)
