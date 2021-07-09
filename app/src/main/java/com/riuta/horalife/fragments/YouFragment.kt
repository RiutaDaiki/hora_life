package com.riuta.horalife.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.riuta.horalife.R
import com.riuta.horalife.databinding.YouFragmentBinding
import com.riuta.horalife.viewModel.DiaryViewModel
import com.riuta.horalife.viewModel.YouViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.riuta.horalife.dialog.BirthDayPicker
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period

class YouFragment : Fragment() {
    private lateinit var binding: YouFragmentBinding
    private val SIGN_IN = 9001
    private val viewModel: YouViewModel by activityViewModels()
    private val currentUser = Firebase.auth.currentUser
    private val diaryViewModel: DiaryViewModel by activityViewModels()
    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build()
    )

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = YouFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

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

    private fun logInFun() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN
        )
    }

    private fun getUserAge(): Int {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return -1
        val userAge = sharedPref.getInt(getString(R.string.user_age), -1)
        return userAge
    }

    fun updateUserAge(userAge: Int) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt(getString(R.string.user_age), userAge)
            commit()
        }
    }

    private fun updateBirthDay(birthday: LocalDate){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.birthday), birthday.toString())
            commit()
        }
    }

    fun calcAge(birthday: LocalDate): Int {
        val today = LocalDate.now()
        return Period.between(LocalDate.parse(birthday.toString()), today).getYears()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN) {
            val user = Firebase.auth.currentUser

            if (user != null) {
                diaryViewModel.currentAccount.value = user
                showLogoutTxt()

                if (!user.isEmailVerified) user.sendEmailVerification()
                    .addOnSuccessListener {
                        Toast.makeText(
                            context,
                            "認証用メールを送信しました。リンクにアクセスし、再度ログインしてください",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
            if (user != null && viewModel.callExisting(user.uid)) {
                viewModel.callCreateUser(user.email, user.uid, user.displayName)
            }
        }
    }

    fun showLoginTxt() {
        viewModel.displayName.value = "ゲストユーザー"
        viewModel.statusText.value = "ログイン"
        viewModel.statusTextColor.value = R.color.blue
        binding.statusText.setOnClickListener {
            if (getUserAge() == -1) BirthDayPicker().show(parentFragmentManager, null)
            else if (getUserAge() > 12) logInFun()
            else if (getUserAge() < 13) Toast.makeText(
                this.requireContext(),
                "ユーザー設定から生年月日を再設定できます。",
                Toast.LENGTH_LONG
            ).show()

            viewModel.userBirthDay.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    updateUserAge(calcAge(it))
                    updateBirthDay(it)
                }
                if (calcAge(it) > 12) logInFun()
                else Toast.makeText(
                    this.requireContext(),
                    "設定から生年月日を再設定できます。",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    fun showLogoutTxt() {
        viewModel.displayName.value = currentUser?.displayName
        viewModel.statusText.value = "ログアウト"
        viewModel.statusTextColor.value = R.color.red

        binding.statusText.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this.requireContext())
                .addOnSuccessListener {
                    showLoginTxt()
                    Toast.makeText(this.requireContext(), "ログアウト完了", Toast.LENGTH_SHORT).show()
                }

        }
    }

}