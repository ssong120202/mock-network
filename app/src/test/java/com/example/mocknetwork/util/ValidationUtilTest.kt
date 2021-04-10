package com.example.mocknetwork.util

import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class ValidationUtilTest {

    @RunWith(Parameterized::class)
    class PatternTest(private var userNameOrPassword: String, private var expectedResult: Boolean) {
        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): Collection<Array<Any>> {
                return listOf(arrayOf("123", false), arrayOf("", false), arrayOf("123456s", true), arrayOf("ssssssss", false))
            }
        }

        @Test
        fun shouldValidatePhoneNumber() {
            assertEquals(expectedResult, ValidationUtil.isValidUsernameAndPassword(userNameOrPassword))
        }
    }
}