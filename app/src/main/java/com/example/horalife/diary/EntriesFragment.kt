 package com.example.horalife.diary

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.horalife.MainActivity
import com.example.horalife.R
import com.example.horalife.camera.CameraFragment
import com.example.horalife.databinding.EntriesFragmentBinding
import com.example.horalife.library.LibraryFragment
import java.time.LocalDate
 private val REQUEST_CODE = 1000

class EntrieFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val binding = EntriesFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.diaryCancelBtn.setOnClickListener(){
            showCancelConfirm()
        }

        if(Build.VERSION.SDK_INT >= 23){
            val permissions = arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA
            )
            checkPermission(permissions, REQUEST_CODE)
        }

        binding.camera.setOnClickListener(){
            openVideoIntent()
        }
        binding.mic.setOnClickListener(){
            openMicIntent()
        }
        binding.dateText.setText(LocalDate.now().toString())

        return binding.root
    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE -> for (i in 0..permissions.size){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context, "パーミッションクリア", Toast.LENGTH_SHORT)
                } else {
                    Toast.makeText(context, "パーミッションフェイルど", Toast.LENGTH_SHORT)

                }
            }
        }
    }

    private fun showCancelConfirm(){
        val dialog = CancelDialogFragment()
        dialog.show(parentFragmentManager, null)
    }
    private fun checkPermission(permissions: Array<String>, request_code: Int){
        ActivityCompat.requestPermissions(this.requireActivity(), permissions, request_code)
    }

    fun openVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(this.requireContext().packageManager).also {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            }
        }
    }

    fun openMicIntent() {
        Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION).also { takePictureIntent ->
            takePictureIntent.resolveActivity(this.requireContext().packageManager).also {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            }
        }
    }
}


































