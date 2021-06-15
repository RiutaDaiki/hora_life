package com.example.horalife.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.horalife.R
import com.example.horalife.databinding.SettingFragmentBinding
import com.example.horalife.dialog.AccountDeleteDialog
import com.example.horalife.viewModel.YouViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("StaticFieldLeak")
private lateinit var binding: SettingFragmentBinding


class SettingFragment: Fragment() {
    private val viewModel: YouViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.isDeleteAccount.observe(viewLifecycleOwner){
            Toast.makeText(context, "成功", Toast.LENGTH_LONG).show()
        }
        val user = Firebase.auth.currentUser
        if (user != null){
            binding.deleteText.setOnClickListener {
                val dialog = AccountDeleteDialog()
                dialog.show(parentFragmentManager, null)
            }
        }
        return binding.root
    }
    fun moveToExample(){
        findNavController().navigate(R.id.action_setting_to_example)
    }
}