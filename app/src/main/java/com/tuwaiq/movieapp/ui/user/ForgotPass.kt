package com.tuwaiq.movieapp.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.movieapp.R


class ForgotPass : Fragment(R.layout.fragment_forgot_pass) {
    private lateinit var btnForgotPass: Button
    private lateinit var emailEd: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btnForgotPass = view.findViewById(R.id.btnSendPass)
        emailEd = view.findViewById(R.id.txtEmailForgot)

        btnForgotPass.setOnClickListener {
            sendTheEmail()
        }
    }

    private fun sendTheEmail() {

        val email = emailEd.text.toString().trim { it <= ' ' }

        if (email.isEmpty()) {
            Toast.makeText(context, "Please enter your E-mail", Toast.LENGTH_SHORT).show()
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context, "E-mail send successful to reset your password",
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().navigate(R.id.action_forgot_pass_to_sign_in)
                    } else {
                        Toast.makeText(
                            context, "The email wasn't correct",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}