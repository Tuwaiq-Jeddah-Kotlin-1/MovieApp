package com.tuwaiq.movieapp.ui.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.movieapp.R

class SignIn : Fragment(R.layout.fragment_sign_in) {
    private lateinit var doNotHaveAc: TextView
    private lateinit var txtForgotPassword: TextView
    private lateinit var btnSignIn: Button
    private lateinit var emailEd: TextInputEditText
    private lateinit var passwordEd: TextInputEditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var rememberMe: CheckBox
    private var checkBoxValue = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPreferences =
            this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)
        doNotHaveAc = view.findViewById(R.id.txt_dont_have_account)
        txtForgotPassword = view.findViewById(R.id.txt_forgot_pass)
        btnSignIn = view.findViewById(R.id.btnSignup)
        emailEd = view.findViewById(R.id.emailED_in)
        passwordEd = view.findViewById(R.id.passwordEd_in)
        rememberMe = view.findViewById(R.id.checkBox)

        checkBoxValue = sharedPreferences.getBoolean("CHECKBOX", false)
        if (checkBoxValue) {
            //findNavController().navigate(R.id.action_sign_in_to_movieFragment)
        }

        doNotHaveAc.setOnClickListener {
            findNavController().navigate(R.id.action_sign_in_to_sign_up)

        }
        txtForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_sign_in_to_forgot_pass)

        }
        btnSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val email: String = emailEd.text.toString().trim { it <= ' ' }
        val password = passwordEd.text.toString().trim { it <= ' ' }

        if (email.isNotEmpty() && password.isNotEmpty()) {
            //save to the Authentication
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG).show()
                        //save user Preference
                        editor = sharedPreferences.edit()
                        editor.putString("EMAIL", email)
                        editor.putString("PASSWORD", password)
                        editor.putBoolean("CHECKBOX", rememberMe.isChecked)
                        editor.apply()

                        findNavController().navigate(R.id.action_sign_in_to_movieFragment)
                    } else {
                        // if the registration is not successful then show error massage
                        Toast.makeText(context,
                            "Please make sure the values are correct, or fill the fields",
                            Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(context, "please enter all fields", Toast.LENGTH_LONG).show()
        }
    }
}