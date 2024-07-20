package com.ahyaemon.application

/**
 * トランザクションを管理する。
 */
interface TransactionManager {

    /**
     * トランザクションの開始を明示的に記述し、スコープを抜けることでトランザクションを終了する。
     * ```kotlin
     * begin {
     *   // Do something
     * }
     * ```
     * @param f トランザクション内で実行する関数
     * @return f の戻り値
     */
    fun <T> begin(f: () -> T): T
}
