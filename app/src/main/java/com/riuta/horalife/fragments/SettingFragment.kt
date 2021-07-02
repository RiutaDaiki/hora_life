package com.riuta.horalife.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        when(isDarkTheme()){
            true -> binding.themeSwitch.isChecked = true
            false -> binding.themeSwitch.isChecked = false
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            when(isChecked) {
                true -> {
                    setDefaultNightMode(MODE_NIGHT_YES)
                    viewModel.isDarkTheme.value = true
                }
                false -> {
                    setDefaultNightMode(MODE_NIGHT_NO)
                    viewModel.isDarkTheme.value = false
                }
            }
        }

        viewModel.isDarkTheme.observe(viewLifecycleOwner){
            lifecycleScope.launch(Dispatchers.IO) {
                updateThemeSetting(it)
            }
        }

        binding.deleteText.setOnClickListener {
            lifecycleScope.launch {
                val dialog = AccountDeleteDialog()
                dialog.show(parentFragmentManager, null)
                viewModel.isDeleteUser.collect {
                    if (it) Toast.makeText(context, resources.getString(R.string.deleted_account), Toast.LENGTH_LONG).show()
                    else Toast.makeText(
                        context,
                        resources.getString(R.string.fail_to_delete_account),
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
                            if (it) Toast.makeText(context, resources.getString(R.string.send_verify_email), Toast.LENGTH_LONG).show()
                        }
                    }
                } else Toast.makeText(context, resources.getString(R.string.verified_email), Toast.LENGTH_SHORT).show()
            }
        }
        
        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    fun updateThemeSetting(isDarkTheme: Boolean){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.current_display_theme), isDarkTheme)
            commit()
        }
    }

    fun isDarkTheme(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    private fun navToYou() {
        findNavController().navigate(R.id.action_setting_to_you)
    }
}