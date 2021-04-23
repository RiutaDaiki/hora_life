package com.example.horalife.library

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.horalife.R

class SelectDialogFragment(): DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val dialogView = inflater.inflate(R.layout.select_dialog, null)
        val list = arrayOf("カメラ","録音")
        var selectItem: Int = 0

        builder.setSingleChoiceItems(list, 0, {dialog, which ->
            selectItem = which
        })
                .setPositiveButton("OK"){dialog, _ ->
                    println(list[selectItem])
                    //API起動
                }
                .setNegativeButton("キャンセル"){dialog, _ ->
                    dialog.dismiss()
                }
        return builder.create()
    }
}