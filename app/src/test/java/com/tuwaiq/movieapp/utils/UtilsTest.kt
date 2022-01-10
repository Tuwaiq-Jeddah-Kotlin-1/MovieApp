package com.tuwaiq.movieapp.utils

import org.junit.Assert.*

import org.junit.Test
import java.util.regex.Pattern

class UtilsTest {

    val emailRegex = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun checkValidationIn(email: String, pass: String): Boolean {
        val isEmailValid: Boolean
        val isPasswordValid: Boolean
        when {
            email.isEmpty() -> {
                isEmailValid = false
            }
            !emailRegex.matcher(email).matches() -> {
                isEmailValid = false
            }
            else -> {
                isEmailValid = true
            }
        }
        when {
            pass.isEmpty() -> {
                isPasswordValid = false
            }
            pass.length < 5 -> {
                isPasswordValid = false
            }
            else -> {
                isPasswordValid = true
            }
        }
        return isEmailValid && isPasswordValid
    }

    @Test
    fun checkValidationIn() {

        val case1 = checkValidationIn("faisal@gmail.com", "123456")
        val case2 = checkValidationIn("faisalgmail.com", "123456")
        val case3 = checkValidationIn("faisal@gmail.com", "1256")
        assertTrue(case1)
        assertFalse(case2)
        assertFalse(case3)
    }
}