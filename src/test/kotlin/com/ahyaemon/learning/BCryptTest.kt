package com.ahyaemon.learning

import org.mindrot.jbcrypt.BCrypt.*
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BCryptTest {

    @Test
    fun test() {
        val rawPassword = "raw_password"
        // 4 <= log_rounds <= 30 の模様
        val salt = gensalt(4)
        val hashed = hashpw(rawPassword, salt)

        println("salt: $salt")
        println("hashed: $hashed")

        assertTrue { checkpw(rawPassword, hashed) }
        assertFalse { checkpw("aaa", hashed) }
    }
}
