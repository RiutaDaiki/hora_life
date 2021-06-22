package com.example.horalife.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.horalife.R
import com.example.horalife.databinding.SettingFragmentBinding
import com.example.horalife.dialog.AccountDeleteDialog
import com.example.horalife.viewModel.YouViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@SuppressLint("StaticFieldLeak")
private lateinit var binding: SettingFragmentBinding

class SettingFragment : Fragment() {
    private val viewModel: YouViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val user = Firebase.auth.currentUser
        if (user == null) {
            binding.constraintLayout.visibility = androidx.constraintlayout.widget.Group.INVISIBLE
            binding.notLoginText.text = "設定する項目がありません、\nログインしてください"
        }

        binding.deleteText.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val dialog = AccountDeleteDialog()
                dialog.show(parentFragmentManager, null)
                viewModel.isDeleteUser.collect {
                    if (it) Toast.makeText(context, "アカウントを削除しました", Toast.LENGTH_LONG).show()
                    else Toast.makeText(
                        context,
                        "アカウントを削除できませんでした。再度ログイン後削除してください",
                        Toast.LENGTH_LONG
                    ).show()
                    navToYou()
                }
            }
        }
        binding.verifyText.setOnClickListener {
            viewModel.sendVerify()
            viewLifecycleOwner.lifecycleScope.launch{
                viewModel.isSendVerifyMail.collect {
                    if (it) Toast.makeText(context, "認証用メールを送信しました", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    private fun navToYou() {
        findNavController().navigate(R.id.action_setting_to_you)
    }
}