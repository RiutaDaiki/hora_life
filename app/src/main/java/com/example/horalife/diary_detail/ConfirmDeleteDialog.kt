package com.example.horalife.diary_detail

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.horalife.diary.DiaryViewModel

class ConfirmDeleteDialog : DialogFragment() {
    private val viewModel: DiaryViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
                .setMessage("この日記を削除しますか？")
                .setPositiveButton("削除") { dialog, which ->
                    viewModel.deleteDocument()
                }
                .setNegativeButton("キャンセル") { dialog, which ->

                }
        return builder.create()
    }

}