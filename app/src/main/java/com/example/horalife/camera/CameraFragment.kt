package com.example.horalife.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.horalife.databinding.CameraFragmentBinding
import java.time.LocalDate

private val REQUEST_CAMERA__PERMISSION = 100

class CameraFragment: Fragment(){
    private var permissionToCameraAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.CAMERA)
    private lateinit var binding : CameraFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
            } else {
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(permissions, REQUEST_CAMERA__PERMISSION)
            }
        }
        binding = CameraFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        dispatchTakeVideoIntent()
        val data = LocalDate.now()

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

     fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(this.requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_CAMERA__PERMISSION)
            }
        }
    }




}



































































































































































































