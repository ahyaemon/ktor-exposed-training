package com.ahyaemon.application

/**
 * Application 層で発生する例外。
 * 他の詳細な例外の親として使う。
 * NOTE sealed class でも良いかも。
 */
open class ApplicationException(message: String) : RuntimeException(message)

/**
 * ログイン失敗。
 */
class LoginFailedException(message: String) : ApplicationException(message)

/**
 * 対象のデータが見つからない。
 */
open class NotFoundException(message: String) : ApplicationException(message)

/**
 * 対象のユーザーが見つからない。
 */
class UserNotFoundException(message: String) : NotFoundException(message)

/**
 * ユーザーが既に存在している。
 */
class UserAlreadyExistsException(message: String) : ApplicationException(message)
