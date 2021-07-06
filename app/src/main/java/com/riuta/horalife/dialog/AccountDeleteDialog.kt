package com.riuta.horalife.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.riuta.horalife.viewModel.YouViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountDeleteDialog() : DialogFragment() {
    private val viewModel: YouViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val user = Firebase.auth.currentUser
        val builder = AlertDialog.Builder(context)
            .setMessage("${user.displayName}\n${user.email}\nこのアカウントを削除しますか？")
            .setPositiveButton("削除する") { dialog, which ->
                viewModel.deleteUser()
            }
            .setNegativeButton("キャンセル") { dialog, which ->
                dialog.dismiss()
            }
        return builder.create()
    }
}