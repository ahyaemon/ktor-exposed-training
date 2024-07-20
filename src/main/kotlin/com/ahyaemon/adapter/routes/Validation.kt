package com.ahyaemon.adapter.routes

import io.ktor.server.plugins.requestvalidation.ValidationResult

/**
 * Ktor によるバリデーションを実行するための関数を作成する。
 * @param f バリデーションエラー時に IllegalArgumentException を投げる関数
 * @return Ktor バリデーションに使用する関数
 */
fun <T> createValidation(f: (T) -> Unit): (T) -> ValidationResult {
    return { request: T ->
        try {
            f(request)
            ValidationResult.Valid
        } catch (e: IllegalArgumentException) {
            ValidationResult.Invalid(e.localizedMessage)
        }
    }
}
