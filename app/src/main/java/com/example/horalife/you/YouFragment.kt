package com.example.horalife.you

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.horalife.databinding.YouFragmentBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class YouFragment : Fragment() {
    private lateinit var binding: YouFragmentBinding
    private val SIGN_IN = 9001

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = YouFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val currentUser = Firebase.auth.currentUser

        if (currentUser != null) {
            binding.user.text = currentUser.displayName
            binding.statusText.visibility = View.INVISIBLE
        } else {
            binding.user.text = "ログインしてません"
        }


        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        binding.statusText.setOnClickListener {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                SIGN_IN
            )
        }

        return binding.root
    }


}