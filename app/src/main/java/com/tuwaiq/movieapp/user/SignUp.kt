package com.tuwaiq.movieapp.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.movieapp.R
import com.tuwaiq.movieapp.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUp : Fragment(R.layout.fragment_sign_up) {
    private lateinit var haveAcc: TextView
    private lateinit var userName: TextInputEditText
    private lateinit var txtEmail1: TextInputEditText
    private lateinit var txtPass1: TextInputEditText
    private lateinit var phoneNum: TextInputEditText
    private lateinit var btnSignUp: Button
    private val db = Firebase.firestore.collection("UserAccount")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //back to sign in
        haveAcc = view.findViewById(R.id.txt_have_account)
        haveAcc.setOnClickListener {
            val action: NavDirections = SignUpDirections.actionSignUpToSignIn()
            view.findNavController().navigate(action)
        }

        userName = view.findViewById(R.id.userNameEd)
        txtEmail1 = view.findViewById(R.id.emailEd)
        phoneNum = view.findViewById(R.id.PhoneEd)
        txtPass1 = view.findViewById(R.id.passwordEd)

        //sign up
        btnSignUp = view.findViewById(R.id.btnSignup)
        btnSignUp.setOnClickListener {
            registerUser()
        }


    }

    // registerUser()
    private fun registerUser() {
        val userName = userName.text.toString()
        val email: String = txtEmail1.text.toString().trim { it <= ' ' }
        val password = txtPass1.text.toString().trim { it <= ' ' }
        //Phone number must be 10
        val phoneNumber = phoneNum.text.toString()
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
                val action: NavDirections = SignUpDirections.actionSignUpToMovieFragment()
                view?.findNavController()?.navigate(action)
                Toast.makeText(context, "saved data", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}