package com.tuwaiq.movieapp.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileViewModel : ViewModel() {
    private val _navigateScreen = MutableLiveData<NavDirections>()
    val navigateScreen: LiveData<NavDirections> = _navigateScreen

    fun saveProfile(userName: String, phone: String) {
        val uId = FirebaseAuth.getInstance().currentUser?.uid
        val upDateUserData = Firebase.firestore.collection("UserAccount")
        upDateUserData.document(uId.toString())
            .update("number", phone, "userName", userName)
    }
}