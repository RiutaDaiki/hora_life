package com.example.horalife

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.horalife.databinding.CameraFragmentBinding

private val REQUEST_CAMERA__PERMISSION = 100

class CameraFragment: Fragment(){
    private var permissionToCameraAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.CAMERA)

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
                println("ダイアログ表示")
            }
        }
        val binding = CameraFragmentBinding.inflate(layoutInflater, container, false)
        val root = inflater.inflate(R.layout.camera_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val context = this.requireContext()
        

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
}