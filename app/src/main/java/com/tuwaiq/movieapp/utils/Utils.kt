package com.tuwaiq.movieapp.utils


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.regex.Pattern.compile

object Utils {

    const val FIELDS_MUST_BE_FILLED = "fields must be filled"
    const val PASSWORD_COUNT = "Password must be more than 8 numbers"
    const val PHONE_COUNT = "PhoneNumber must be 10 numbers"
    const val WRONG_EMAIL = "Email is wrong"
    private val statusMessage = MutableLiveData<String>()
    val message: LiveData<String> = statusMessage
    private val emailRegex = compile(
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
                statusMessage.value = FIELDS_MUST_BE_FILLED
                isEmailValid = false
            }
            !emailRegex.matcher(email).matches() -> {
                statusMessage.value =   WRONG_EMAIL
                isEmailValid = false
            }
            else -> {
                isEmailValid = true
            }
        }
        when {
            pass.isEmpty() -> {
                statusMessage.value = FIELDS_MUST_BE_FILLED
                isPasswordValid = false
            }
            pass.length < 5 -> {
                statusMessage.value = PASSWORD_COUNT
                isPasswordValid = false
            }
            else -> {
                isPasswordValid = true
            }
        }
        return isEmailValid && isPasswordValid
    }


    fun checkValidationUP(email: String, pass: String, phone: String, userName: String): Boolean {
        val isEmailValid: Boolean
        val isPasswordValid: Boolean
        val isPhoneValid: Boolean
        val isUserNameValid: Boolean
        when {
            email.isEmpty() -> {
                statusMessage.value = FIELDS_MUST_BE_FILLED
                isEmailValid = false
            }
            !emailRegex.matcher(email).matches() -> {
                statusMessage.value = WRONG_EMAIL
                isEmailValid = false
            }
            else -> {
                isEmailValid = true
            }
        }
        when {
            pass.isEmpty() -> {
                statusMessage.value = FIELDS_MUST_BE_FILLED
                isPasswordValid = false
            }
            pass.length < 8 -> {
                statusMessage.value = PASSWORD_COUNT
                isPasswordValid = false
            }
            else -> {
                isPasswordValid = true
            }
        }
        when {
            phone.isEmpty() -> {
                statusMessage.value = FIELDS_MUST_BE_FILLED
                isPhoneValid = false
            }
            phone.length == 10 -> {
                isPhoneValid = true
            }
            else -> {
                statusMessage.value = PHONE_COUNT
                isPhoneValid = false
            }
        }
        when {
            userName.isEmpty() -> {
                statusMessage.value = FIELDS_MUST_BE_FILLED
                isUserNameValid = false
            }

            else -> {
                isUserNameValid = true
            }
        }
        return isEmailValid && isPasswordValid && isPhoneValid && isUserNameValid
    }
}