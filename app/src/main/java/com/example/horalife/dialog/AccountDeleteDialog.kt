package com.example.horalife.dialog

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountDeleteDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val user = Firebase.auth.currentUser
        val builder = AlertDialog.Builder(context)
            .setMessage("${user.displayName}\n${user.email}\nこのアカウントを削除しますか？")
            .setPositiveButton("削除") { dialog, which ->
                AuthUI.getInstance()
                    .delete(this.requireContext())
                    .addOnCompleteListener {
                        Toast.makeText(this.requireContext(), "アカウントを削除しました", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this.requireContext(), "エラー:アカウントの削除に失敗しました", Toast.LENGTH_LONG).show()

                    }
            }
        return builder.create()
    }
}