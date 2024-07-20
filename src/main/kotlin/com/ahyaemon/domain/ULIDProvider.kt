package com.ahyaemon.domain

import com.github.f4b6a3.ulid.Ulid
import com.github.f4b6a3.ulid.UlidCreator

object ULIDProvider {

    /**
     * ULID を作成する。
     * @return ULID
     */
    fun generate(): String {
        return UlidCreator.getUlid().toString()
    }

    /**
     * 正しい ULID 形式かどうか検証する。
     * @return 正しい ULID 形式の場合 true
     */
    fun isValid(s: String): Boolean {
        return Ulid.isValid(s)
    }
}
