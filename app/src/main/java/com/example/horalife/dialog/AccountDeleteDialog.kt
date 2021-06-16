package com.example.horalife.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.horalife.viewModel.YouViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect

class AccountDeleteDialog(): DialogFragment() {
    private val viewModel : YouViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val user = Firebase.auth.currentUser
        val builder = AlertDialog.Builder(context)
            .setMessage("${user.displayName}\n${user.email}\nこのアカウントを削除しますか？")
            .setPositiveButton("削除") { dialog, which ->
                viewModel.deleteUser()
            }
        return builder.create()
    }
}