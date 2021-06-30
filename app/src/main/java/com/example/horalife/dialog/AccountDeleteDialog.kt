package com.example.horalife.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.horalife.fragments.YouFragment
import com.example.horalife.viewModel.YouViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AccountDeleteDialog() : DialogFragment() {
    private val viewModel: YouViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
            .setMessage("${viewModel.displayName.value}\n${viewModel.email.value}\nこのアカウントを削除しますか？")
            .setPositiveButton("削除する") { dialog, which ->
                viewModel.deleteUser()
            }
            .setNegativeButton("キャンセル") { dialog, which ->
                dialog.dismiss()
            }
        return builder.create()
    }
}