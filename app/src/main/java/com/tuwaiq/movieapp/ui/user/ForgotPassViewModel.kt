package com.tuwaiq.movieapp.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.google.firebase.auth.FirebaseAuth

class ForgotPassViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _navigateScreen = MutableLiveData<NavDirections>()
    val navigateScreen: LiveData<NavDirections> = _navigateScreen
    private val statusMessage = MutableLiveData<String>()
    val message: LiveData<String> = statusMessage

    fun sendTheEmail(email: String) {

        if (email.isEmpty()) {
            statusMessage.value = "Please enter your E-mail"
        } else {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        statusMessage.value = "E-mail send successful to reset your password"
                        _navigateScreen.value = ForgotPassDirections.actionForgotPassToSignIn()
                    } else {
                        statusMessage.value = "The email wasn't correct"
                    }
                }
        }
    }
}