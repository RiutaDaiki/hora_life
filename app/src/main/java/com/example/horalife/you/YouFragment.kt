package com.example.horalife.you

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.horalife.R
import com.example.horalife.databinding.YouFragmentBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class YouFragment : Fragment() {
    private lateinit var binding: YouFragmentBinding
    private val SIGN_IN = 9001
    private val viewModel: YouViewModel by viewModels()

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = YouFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val currentUser = Firebase.auth.currentUser

        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
        )

        if (currentUser != null) {
            //ログインしている状態　
            binding.user.text = currentUser.displayName
            binding.statusText.text = "ログアウト"
            binding.statusText.setTextColor(statusColor("red"))
            binding.statusText.setOnClickListener {
                AuthUI.getInstance()
                        .signOut(this.requireContext())
                        .addOnCompleteListener {
                            Toast.makeText(this.requireContext(), "ログアウト完了", Toast.LENGTH_SHORT).show()
                        }
            }
        } else {
            binding.user.text = "ログインしてません"
            binding.statusText.text = "ログイン・登録"
            binding.statusText.setTextColor(statusColor("blue"))
            binding.statusText.setOnClickListener {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        SIGN_IN)
            }
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN) {
            val user = Firebase.auth.currentUser
            if (user != null) Toast.makeText(context, "ログイン完了", Toast.LENGTH_SHORT).show()
            //ここでログインしたユーザーが既存のユーザなのか新規登録したユーザなのか判定
            //新規ユーザーならfirestoreに保存する
            if (viewModel.callExisting(user.uid)) {
                viewModel.callCreateUser(user.email, user.uid, user.displayName)
            }
        }
    }

    private fun statusColor(color: String): Int {
        var result = resources.getColor(R.color.black)
        when (color) {
            "blue" -> result = resources.getColor(R.color.blue)
            "red" -> result = resources.getColor(R.color.red)
        }
        return result
    }
}