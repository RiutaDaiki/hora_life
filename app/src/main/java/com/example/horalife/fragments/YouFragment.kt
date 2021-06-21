package com.example.horalife.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.horalife.R
import com.example.horalife.databinding.YouFragmentBinding
import com.example.horalife.viewModel.DiaryViewModel
import com.example.horalife.viewModel.YouViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope

class YouFragment : Fragment() {
    private lateinit var binding: YouFragmentBinding
    private val SIGN_IN = 9001
    private val viewModel: YouViewModel by viewModels()
    private val currentUser = Firebase.auth.currentUser
    private val diaryViewModel: DiaryViewModel by activityViewModels()

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = YouFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )


        fun showLogoutTxt() {
            binding.user.text = currentUser.displayName
            binding.statusText.text = "ログアウト"
            binding.statusText.setTextColor(resources.getColor(R.color.red))
            binding.statusText.setOnClickListener {
                AuthUI.getInstance()
                    .signOut(this.requireContext())
                    .addOnSuccessListener {
                        findNavController().navigate(R.id.nav_example)
                        Toast.makeText(this.requireContext(), "ログアウト完了", Toast.LENGTH_SHORT).show()
                    }

            }
        }

        fun showLoginTxt() {
            binding.user.text = "ログインしてません"
            binding.statusText.text = "ログイン・登録"
            binding.statusText.setTextColor(resources.getColor(R.color.blue))
            binding.statusText.setOnClickListener {
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                    SIGN_IN
                )
            }
        }

        if (currentUser != null) {
            showLogoutTxt()
        } else {
            showLoginTxt()
        }

        binding.settingText.setOnClickListener {
            findNavController().navigate(R.id.action_nav_you_to_setting)
        }

        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN) {
            val user = Firebase.auth.currentUser

            if (user != null) {
                diaryViewModel.currentAccount.value = user
                findNavController().navigate(R.id.nav_example)
                Toast.makeText(context, "ログイン完了", Toast.LENGTH_SHORT).show()
            }
            if (user != null && viewModel.callExisting(user.uid)) {
                viewModel.callCreateUser(user.email, user.uid, user.displayName)
            }
        }
    }
}