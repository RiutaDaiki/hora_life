package com.example.horalife.diary_entries

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.horalife.R

class AuthDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
                .setMessage("日記機能を有効にするにはログインする必要があります。")
                .setPositiveButton("ログイン画面へ") { dialog, which ->
                    findNavController().navigate(R.id.nav_you)
                }
                .setNegativeButton("キャンセル") { dialog, which ->

                }
        return builder.create()
    }
}