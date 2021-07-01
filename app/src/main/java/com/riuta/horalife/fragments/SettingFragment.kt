package com.riuta.horalife.fragments

import android.app.Activity
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.riuta.horalife.R
import com.riuta.horalife.databinding.SettingFragmentBinding
import com.riuta.horalife.dialog.AccountDeleteDialog
import com.riuta.horalife.viewModel.YouViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.riuta.horalife.MainActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class SettingFragment : Fragment() {
    private lateinit var binding: SettingFragmentBinding

    private val viewModel: YouViewModel by activityViewModels()

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
            binding.notLoginText.text = resources.getString(R.string.no_setting)
        }
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            when(isChecked) {
                true -> setDefaultNightMode(MODE_NIGHT_YES)
                false -> setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        when(isDarkTheme()){
            true -> binding.themeSwitch.isChecked = true
            false -> binding.themeSwitch.isChecked = false
        }

        binding.deleteText.setOnClickListener {
            lifecycleScope.launch {
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
            if(user != null){
                if (!user.isEmailVerified) {
                    viewModel.sendVerify()
                    lifecycleScope.launch {
                        viewModel.isSendVerifyMail.collect {
                            if (it) Toast.makeText(context, "認証用メールを送信しました。メールに添付されたリンクをアクセスし、再度ログインしてください", Toast.LENGTH_LONG).show()
                        }
                    }
                } else Toast.makeText(context, "メールアドレス認証済み", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    fun isDarkTheme(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    private fun navToYou() {
        findNavController().navigate(R.id.action_setting_to_you)
    }
}