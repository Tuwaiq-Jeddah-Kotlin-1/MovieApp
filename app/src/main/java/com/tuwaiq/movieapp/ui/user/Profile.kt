package com.tuwaiq.movieapp.ui.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.movieapp.R
import com.tuwaiq.movieapp.databinding.FragmentProfileBinding
import com.tuwaiq.movieapp.utils.GetUserInfo

class Profile : Fragment(R.layout.fragment_profile) {

    private val viewModel by viewModels<ProfileViewModel>()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        sharedPreferences =
            this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)
        val sp1 = sharedPreferences.getString("spUserName", " ")
        val sp2 = sharedPreferences.getString("spEmail", " ")
        val sp3 = sharedPreferences.getString("spPhoneNumber", " ")

        with(binding) {
            userNameProfile.setText(sp1)
            emailProfile.setText(sp2)
            PhoneProfile.setText(sp3)

            emailProfile.isEnabled = false

            viewModel.navigateScreen.observe(viewLifecycleOwner, {
                findNavController().navigate(it)
            })

            signOut.setOnClickListener {
                auth.signOut()
                findNavController().navigate(ProfileDirections.actionNavProfileToSignIn())
            }
            btnSaveEdit.setOnClickListener {
                viewModel.saveProfile(userNameProfile.text.toString(), PhoneProfile.text.toString())
                GetUserInfo().getUserInfo(auth.uid.toString()).observe(viewLifecycleOwner, {
                    sharedPreferences.edit()
                        .putString("spEmail", it.email)
                        .putString("spPhoneNumber", it.number)
                        .putString("spUserName", it.userName)
                        .apply()
                })
            }
        }
    }
}