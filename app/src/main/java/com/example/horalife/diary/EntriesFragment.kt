package com.example.horalife.diary

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.horalife.R
import com.example.horalife.camera.CameraFragment
import com.example.horalife.databinding.EntriesFragmentBinding
import java.time.LocalDate

private val REQUEST_CAMERA__PERMISSION = 100
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200


class EntrieFragment: Fragment() {
    private var permissionToCameraAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.CAMERA)
    private var permissionToRecordAccepted = false
    private var recordPermissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                // 許可されている
//            } else {
//                // 許可されていないので許可ダイアログを表示する
//                requestPermissions(permissions, REQUEST_CAMERA__PERMISSION)
//            }
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
//                // 許可されている
//
//            } else {
//                // 許可されていないので許可ダイアログを表示する
//                requestPermissions(recordPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
//                println("ダイアログ表示")
//            }
//        }
        val binding = EntriesFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val data = LocalDate.now()
        Log.i("", data.toString())
        val cancelBtn = binding.root.findViewById<Button>(R.id.diary_cancel_btn)
        cancelBtn.setOnClickListener(){
            showCancelConfirm()
        }
        val camera = binding.root.findViewById<ImageView>(R.id.camera)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
            } else {
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(permissions, REQUEST_CAMERA__PERMISSION)
            }
        }

        camera.setOnClickListener(){
            openTakeVideoIntent()
        }

        return binding.root
    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToCameraAccepted = if (requestCode == REQUEST_CAMERA__PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }

//    override fun onRequestPermissionsResult(
//            requestCode: Int,
//            permissions: Array<String>,
//            grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
//            grantResults[0] == PackageManager.PERMISSION_GRANTED
//        } else {
//            false
//        }
//    }
    private fun showCancelConfirm(){

    }

    fun openTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(this.requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_CAMERA__PERMISSION)
            }
        }
    }


}