package com.example.horalife.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.horalife.R
import com.example.horalife.databinding.SettingFragmentBinding
import com.example.horalife.dialog.AccountDeleteDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("StaticFieldLeak")
private lateinit var binding: SettingFragmentBinding

class SettingFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.deleteText.setOnClickListener {
            val dialog = AccountDeleteDialog {

            }
            val user = Firebase.auth.currentUser
            if (user != null) dialog.show(parentFragmentManager, null)

        }
        return binding.root
    }
    fun moveToExample(){
        findNavController().navigate(R.id.action_setting_to_example)
    }


}