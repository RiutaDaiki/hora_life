package com.example.horalife.diary

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class EntriesDialogFragment(): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val list = arrayOf("カメラ","録音")
        var selectItem = 0

        builder.setSingleChoiceItems(list, 0, {dialog, which ->
            selectItem = which
        })
                .setPositiveButton("削除"){dialog, _ ->

                }
        return builder.create()
    }
}