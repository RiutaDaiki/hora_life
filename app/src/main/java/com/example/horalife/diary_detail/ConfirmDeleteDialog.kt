package com.example.horalife.diary_detail

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmDeleteDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
            .setMessage("この日記を削除しますか？")
            .setPositiveButton("削除"){dialog, which ->

            }
            .setNegativeButton("キャンセル"){dialog, which ->

            }
        return builder.create()
    }

}