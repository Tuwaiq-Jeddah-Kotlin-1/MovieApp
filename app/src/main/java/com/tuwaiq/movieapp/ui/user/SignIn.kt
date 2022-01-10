package com.tuwaiq.movieapp.ui.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.movieapp.R
import com.tuwaiq.movieapp.databinding.FragmentSignInBinding
import com.tuwaiq.movieapp.utils.GetUserInfo
import com.tuwaiq.movieapp.utils.Utils

class SignIn : Fragment(R.layout.fragment_sign_in) {


    private val viewModel by viewModels<SignInViewModel>()
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignInBinding.bind(view)
        sharedPreferences =
            this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)

        with(binding) {

            btnSignIn.setOnClickListener {
                /*GetUserInfo().getUserInfo(auth.uid.toString()).observe(viewLifecycleOwner, {
                    sharedPreferences.edit()
                        .putString("spEmail", it.email)
                        .putString("spPhoneNumber", it.number)
                        .putString("spUserName", it.userName)
                        .apply()
                })*/
                viewModel.signIn(emailIn.text.toString().trim(), passwordIn.text.toString().trim())
            }
            viewModel.message.observe(viewLifecycleOwner, {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })
            viewModel.navigateScreen.observe(viewLifecycleOwner, {
                findNavController().navigate(it)
            })
            accountIn.setOnClickListener {
                findNavController().navigate(R.id.action_sign_in_to_sign_up)
            }
            txtForgotPass.setOnClickListener {
                findNavController().navigate(R.id.action_sign_in_to_forgot_pass)
            }

            Utils.message.observe(viewLifecycleOwner, { massage ->
                    Toast.makeText(context, massage, Toast.LENGTH_LONG).show()
            })

            val remembered = sharedPreferences.getBoolean("remember", false)
            checkBox.setOnClickListener {

                if (checkBox.isChecked) {
                    sharedPreferences.edit()
                        .putBoolean("remember", true)
                        .apply()
                } else {
                    sharedPreferences.edit()
                        .putBoolean("remember", false)
                        .apply()
                }
            }
            val currentUser = auth.currentUser

            checkBox.isChecked = remembered
            if (checkBox.isChecked && currentUser != null) {
                findNavController().navigate(R.id.action_sign_in_to_movieFragment)
            }

        }

    }

}