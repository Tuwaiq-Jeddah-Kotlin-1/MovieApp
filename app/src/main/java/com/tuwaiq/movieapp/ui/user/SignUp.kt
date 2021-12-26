package com.tuwaiq.movieapp.ui.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.movieapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUp : Fragment(R.layout.fragment_sign_up) {
    private lateinit var haveAcc: TextView
    private lateinit var userNameEd: TextInputEditText
    private lateinit var emailEd: TextInputEditText
    private lateinit var passwordEd: TextInputEditText
    private lateinit var phoneEd: TextInputEditText
    private lateinit var btnSignUp: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val db = Firebase.firestore.collection("UserAccount")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        userNameEd = view.findViewById(R.id.userNameEd)
        emailEd = view.findViewById(R.id.emailEd)
        phoneEd = view.findViewById(R.id.PhoneEd)
        passwordEd = view.findViewById(R.id.passwordEd)
        haveAcc = view.findViewById(R.id.txt_have_account)
        btnSignUp = view.findViewById(R.id.btnSignup)

        //sign up
        btnSignUp.setOnClickListener {
            registerUser()
        }
        //back to sign in
        haveAcc.setOnClickListener {
            findNavController().navigate(R.id.action_sign_up_to_sign_in)
        }
    }

    // registerUser()
    private fun registerUser() {
        val userName = userNameEd.text.toString()
        val email: String = emailEd.text.toString().trim { it <= ' ' }
        val password = passwordEd.text.toString().trim { it <= ' ' }
        //Phone number must be 10
        val phoneNumber = phoneEd.text.toString()
        val account = User(userName, email, phoneNumber)

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phoneNumber.isNotEmpty()) {
            //save to the Authentication
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "You were registered successful", Toast.LENGTH_LONG)
                            .show()
                        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        saveAccount(account)
                        sharedPreferences =
                            requireActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE)
                        editor = sharedPreferences.edit()
                        editor.putString("spEmail", email)
                        editor.putString("spPhoneNumber", phoneNumber)
                        editor.putString("spUserName", userName)
                        editor.apply()

                    } else {
                        // if the registration is not successful then show error massage
                        Toast.makeText(
                            context, "Please make sure the values are correct, or fill the fields",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
        } else {
            Toast.makeText(context, "please enter all fields", Toast.LENGTH_LONG)
                .show()
        }
    }

    //push to fire store
    private fun saveAccount(account: User) = CoroutineScope(Dispatchers.IO).launch {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        try {
            db.document("$uid").set(account)
            withContext(Dispatchers.Main) {
                findNavController().navigate(R.id.action_sign_up_to_movieFragment)
                Toast.makeText(context, "saved data", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}