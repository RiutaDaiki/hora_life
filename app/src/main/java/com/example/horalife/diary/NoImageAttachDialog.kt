package com.example.horalife.diary

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class NoImageAttachDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("動画が選択されていません。この内容で記録しますか？")
                .setPositiveButton("記録する"){dialog, which ->
                    EntrieFragment().storeThumbnail()
                }.setNegativeButton("キャンセル"){dialog, which ->  
                    
                }
        return builder.create()
    }
}