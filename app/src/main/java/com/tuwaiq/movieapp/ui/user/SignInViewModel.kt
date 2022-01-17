package com.tuwaiq.movieapp.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.movieapp.utils.Utils.checkValidationIn

class SignInViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _navigateScreen = MutableLiveData<NavDirections>()
    val navigateScreen: LiveData<NavDirections> = _navigateScreen

    private val statusMessage = MutableLiveData<String>()
    val message: LiveData<String> = statusMessage


    fun signIn(email: String, password: String) {

        if (checkValidationIn(email, password)) {
            //save to the Authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        //statusMessage.value = "Sign in successful"
                        _navigateScreen.value = SignInDirections.actionSignInToMovieFragment()
                    } else {
                        // if the registration is not successful then show error massage
                        statusMessage.value =
                            "Please make sure the values are correct, or fill the fields"
                    }
                }
        } else {
            statusMessage.value = "please enter all fields"
        }

    }


}