package com.tuwaiq.movieapp.utils

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.movieapp.ui.user.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetUserInfo() {


    fun getUserInfo(userID: String): LiveData<User> {
        val signedUser = MutableLiveData<User>()
        CoroutineScope(Dispatchers.IO).launch {

            try {
                val db = FirebaseFirestore.getInstance()
                db.collection("UserAccount")
                    .document(userID)
                    .get().addOnCompleteListener {
                        if (it.result?.exists()!!) {
                            //+++++++++++++++++++++++++++++++++++++++++
                            val userEmail = it.result!!.getString("email")
                            val userPhone = it.result!!.getString("number")
                            val userName = it.result!!.getString("userName")//moreInfo
                            signedUser.postValue(User(userName!!, userEmail!!, userPhone!!))
                        } else {
                            Log.e("error \n", "Error")
                        }
                    }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("FUNCTION createUserFirestore", "${e.message}")
                }
            }
        }
        return signedUser
    }

}

