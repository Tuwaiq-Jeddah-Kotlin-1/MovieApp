package com.tuwaiq.movieapp.ui.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.movieapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var userNameProfile: TextInputEditText
    private lateinit var emailProfile: TextInputEditText
    private lateinit var phoneNumberProfile: TextInputEditText
    private lateinit var btnSaveEdit: Button
    private lateinit var logOut: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferences1: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val uId = FirebaseAuth.getInstance().currentUser?.uid
        getUserInfo(uId.toString())
        //shared preference
        sharedPreferences =
            this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)


        userNameProfile = view.findViewById(R.id.txt_userName_profile)
        emailProfile = view.findViewById(R.id.txt_email_Profile)
        phoneNumberProfile = view.findViewById(R.id.txt_Phone_profile)
        btnSaveEdit = view.findViewById(R.id.btnSaveEdit)
        logOut = view.findViewById(R.id.logOut)

        emailProfile.isEnabled = false

        //get the info from the sp
        sharedPreferences = this.requireActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE)
        val sp1 = sharedPreferences.getString("spUserName"," ")
        val sp2 = sharedPreferences.getString("spEmail"," ")
        val sp3 = sharedPreferences.getString("spPhoneNumber"," ")

        userNameProfile.setText(sp1)
        emailProfile.setText(sp2)
        phoneNumberProfile.setText(sp3)

        btnSaveEdit.setOnClickListener {
            saveProfile()
        }
        logOut.setOnClickListener {
            getSPForLogOut()
        }
    }

    fun getUserInfo(userID: String) = CoroutineScope(Dispatchers.IO).launch {

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

                        Log.e("user Info","userName ${userEmail.toString()} \n ${userName.toString()}")

                        sharedPreferences1 = requireActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE)
                        editor = sharedPreferences1.edit()
                        editor.putString("spEmail",userEmail)
                        editor.putString("spPhoneNumber",userPhone)
                        editor.putString("spUserName",userName)
                        editor.apply()

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

    private fun getSPForLogOut() {
        editor = sharedPreferences.edit()
        sharedPreferences.getString("EMAIL", "")
        sharedPreferences.getString("PASSWORD", "")
        editor.clear().apply()
        findNavController().navigate(ProfileFragmentDirections.actionNavProfileToSignIn())
    }

    private fun saveProfile() {
        val uId = FirebaseAuth.getInstance().currentUser?.uid
        val upDateUserData = Firebase.firestore.collection("UserAccount")
        upDateUserData.document(uId.toString())
            .update("number",phoneNumberProfile.text.toString(),"userName",userNameProfile.text.toString())
        Toast.makeText(context, "edit is successful", Toast.LENGTH_LONG).show()

    }
}