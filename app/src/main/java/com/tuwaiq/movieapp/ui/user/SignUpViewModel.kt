package com.tuwaiq.movieapp.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.movieapp.utils.Utils.checkValidationUP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore.collection("UserAccount")
    val emailSP = MutableLiveData<String>()
    val userNameSP = MutableLiveData<String>()
    val phoneSP = MutableLiveData<String>()
    private val _navigateScreen = MutableLiveData<NavDirections>()
    val navigateScreen: LiveData<NavDirections> = _navigateScreen
    private val statusMessage = MutableLiveData<String>()
    val message: LiveData<String> = statusMessage

    // registerUser()
    fun signUp(
        userName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ) {
        val account = User(userName, email, phoneNumber)

        if (checkValidationUP(email, password, phoneNumber, userName)) {
            //save to the Authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                       // statusMessage.value = "You were registered successful"
                        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        saveAccount(account)
                        emailSP.value = email
                        userNameSP.value = userName
                        phoneSP.value = phoneNumber
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

    //push to fire store
    private fun saveAccount(account: User) = CoroutineScope(Dispatchers.IO).launch {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        try {
            db.document("$uid").set(account)
            withContext(Dispatchers.Main) {
                _navigateScreen.value = SignUpDirections.actionSignUpToMovieFragment()
                statusMessage.value = "saved data"
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                statusMessage.value = e.message
            }
        }
    }


}