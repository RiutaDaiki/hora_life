package com.example.horalife.diary

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CancelDialogFragment(): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage("キャンセルしますか？動画、音声、下書きは保存されません。")
                .setPositiveButton("削除"){dialog, _ ->

                }
                .setNegativeButton("削除しない"){dialog, _ ->

                }
        return builder.create()
    }
}