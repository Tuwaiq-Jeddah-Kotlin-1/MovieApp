package com.tuwaiq.movieapp.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.movieapp.R


class ForgotPass : Fragment() {
    private lateinit var sendPassButton: Button
    private lateinit var enterToSendTheEmail : TextInputEditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_pass, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sendPassButton = view.findViewById(R.id.btnSendPass)
        enterToSendTheEmail = view.findViewById(R.id.txtEmailForgot)


        sendPassButton.setOnClickListener {
            sendTheEmail()
        }
    }

    private fun sendTheEmail() {
        val email = enterToSendTheEmail.text.toString().trim { it <= ' ' }

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
                        val action: NavDirections =
                            ForgotPassDirections.actionForgotPassToSignIn()
                        view?.findNavController()?.navigate(action)
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