package com.riuta.horalife.fragments

import android.content.Context
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
import com.riuta.horalife.databinding.SettingFragmentBinding
import com.riuta.horalife.dialog.AccountDeleteDialog
import com.riuta.horalife.viewModel.YouViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.riuta.horalife.dialog.BirthDayPicker
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import java.time.Period

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
        viewModel.birthDay.value = getBirthDay()

        val user = Firebase.auth.currentUser
        if (user == null) {
            binding.categoryAccount.visibility = androidx.constraintlayout.widget.Group.INVISIBLE
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

        viewModel.userBirthDay.observe(viewLifecycleOwner) {
            val realBirthDay = it.plusMonths(1)
            viewModel.birthDay.value = realBirthDay.toString()
            lifecycleScope.launch{
                updateUserAge(calcAge(it))
                updateBirthDay(realBirthDay)
            }
        }
        binding.editBirthday.setOnClickListener {
            BirthDayPicker().show(parentFragmentManager, null)
        }

        return binding.root
    }

    private fun navToYou() {
        findNavController().navigate(R.id.action_setting_to_you)
    }

    private fun updateUserAge(userAge: Int) {
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

    private fun getBirthDay(): String {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return ""
        val birthday = sharedPref.getString(getString(R.string.birthday), "")
        return birthday ?: ""
    }

    private fun calcAge(birthday: LocalDate): Int {
        val today = LocalDate.now()
        return Period.between(LocalDate.parse(birthday.toString()), today).getYears()
    }

}