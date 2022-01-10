package com.tuwaiq.movieapp.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.movieapp.R
import com.tuwaiq.movieapp.databinding.FragmentForgotPassBinding
import com.tuwaiq.movieapp.databinding.FragmentSignInBinding


class ForgotPass : Fragment(R.layout.fragment_forgot_pass) {

    private val viewModel by viewModels<ForgotPassViewModel>()
    private var _binding: FragmentForgotPassBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentForgotPassBinding.bind(view)

        with(binding) {
            viewModel.navigateScreen.observe(viewLifecycleOwner, {
                findNavController().navigate(it)
            })
            viewModel.message.observe(viewLifecycleOwner, {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })
            btnSendPass.setOnClickListener {
                viewModel.sendTheEmail(txtEmailForgot.text.toString())
            }
        }
    }
}