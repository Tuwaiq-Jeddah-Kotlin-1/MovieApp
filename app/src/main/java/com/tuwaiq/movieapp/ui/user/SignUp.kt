package com.tuwaiq.movieapp.ui.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.movieapp.R
import com.tuwaiq.movieapp.databinding.FragmentSignUpBinding
import com.tuwaiq.movieapp.utils.Utils

class SignUp : Fragment(R.layout.fragment_sign_up) {

    private val viewModel by viewModels<SignUpViewModel>()
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    //  private lateinit var btnSignUp: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        sharedPreferences =
            this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)

        with(binding) {
            btnSignUp.setOnClickListener {
                viewModel.signUp(userNameUp.text.toString().trim(),
                    emailUp.text.toString().trim(),
                    PhoneUp.text.toString().trim(), passwordUp.text.toString().trim())
            }
            viewModel.message.observe(viewLifecycleOwner, {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })
            viewModel.navigateScreen.observe(viewLifecycleOwner, {
                findNavController().navigate(it)
            })
            accountUp.setOnClickListener {
                findNavController().navigate(R.id.action_sign_up_to_sign_in)
            }
            viewModel.emailSP.observe(viewLifecycleOwner, {
                sharedPreferences.edit()
                    .putString("spEmail", it).apply()
            })
            viewModel.phoneSP.observe(viewLifecycleOwner, {
                sharedPreferences.edit()
                    .putString("spPhoneNumber", it).apply()
            })
            viewModel.userNameSP.observe(viewLifecycleOwner, {
                sharedPreferences.edit()
                    .putString("spUserName", it).apply()
            })
            Utils.message.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let { massage ->
                    Toast.makeText(context, massage, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}