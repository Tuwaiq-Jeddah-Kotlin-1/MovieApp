package com.tuwaiq.movieapp.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.movieapp.R

class SignIn : Fragment() {
    private lateinit var doNotHaveAc: TextView
    private lateinit var txtForgotPassword: TextView
    private lateinit var btnSignIn: Button
    private lateinit var txtEmail2: TextInputEditText
    private lateinit var txtPass2: TextInputEditText
    private lateinit var sharedPreferences: SharedPreferences
    private var checkBoxValue = false
    private lateinit var rememberMe: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPreferences =
            this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)
        doNotHaveAc = view.findViewById(R.id.txt_dont_have_account)
        txtForgotPassword = view.findViewById(R.id.txt_forgot_pass)
        btnSignIn = view.findViewById(R.id.btnSignup)
        txtEmail2 = view.findViewById(R.id.emailED_in)
        txtPass2 = view.findViewById(R.id.passwordEd_in)
        rememberMe = view.findViewById(R.id.checkBox)

        checkBoxValue = sharedPreferences.getBoolean("CHECKBOX", false)
        if (checkBoxValue) {
            findNavController().navigate(R.id.action_sign_in_to_homeFragment)
        }

        doNotHaveAc.setOnClickListener {
            val action: NavDirections = SignInDirections.actionSignInToSignUp()
            view.findNavController().navigate(action)
        }
        txtForgotPassword.setOnClickListener {
            val action: NavDirections = SignInDirections.actionSignInToForgotPass()
            view.findNavController().navigate(action)
        }
        btnSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val email: String = txtEmail2.text.toString().trim { it <= ' ' }
        val password = txtPass2.text.toString().trim { it <= ' ' }

        if (email.isNotEmpty() && password.isNotEmpty()) {
            //save to the Authentication
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG)
                            .show()

                        val action: NavDirections = SignInDirections.actionSignInToHomeFragment()
                        view?.findNavController()?.navigate(action)
                        checkBox()
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

    fun checkBox() {
        val email: String = txtEmail2.text.toString()
        val pass: String = txtPass2.text.toString()
        val checked: Boolean = rememberMe.isChecked
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("EMAIL", email)
        editor.putString("PASSWORD", pass)
        editor.putBoolean("CHECKBOX", checked)
        editor.apply()
    }

}