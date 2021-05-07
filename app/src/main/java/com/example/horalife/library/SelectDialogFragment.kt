package com.example.horalife.library

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.horalife.camera.CameraFragment
import com.example.horalife.R
import com.example.horalife.mic.RecordFragment

class SelectDialogFragment(): DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val list = arrayOf("カメラ","録音")
        var selectItem: Int = 0

        builder.setSingleChoiceItems(list, 0, {dialog, which ->
            selectItem = which
        })
                .setPositiveButton("OK"){dialog, _ ->
                    cameraOrMic(selectItem)
                }
                .setNegativeButton("キャンセル"){dialog, _ ->
                    dialog.dismiss()
                }
        return builder.create()
    }

    private fun showRecordFragment(){
        val recordFragment = RecordFragment()
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, recordFragment)
        fragmentTransaction.commit()
    }

    private fun showCameraFragment(){
        val cameraFragment = CameraFragment()
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, cameraFragment)
        fragmentTransaction.commit()
    }

    private fun cameraOrMic(int: Int){
        when(int){
            0 -> showCameraFragment()
            1 -> showRecordFragment()
        }
    }
}