package com.example.horalife.camera

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.view.PreviewView
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.horalife.R
import com.example.horalife.camera.Constants.TAG
import com.example.horalife.databinding.CameraFragmentBinding
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getMainExecutor
import java.io.File

private val REQUEST_CAMERA__PERMISSION = 100

class CameraFragment: Fragment(){
    private var permissionToCameraAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.CAMERA)
    private lateinit var viewFinder: PreviewView
    private lateinit var preview: Preview
    private lateinit var binding : CameraFragmentBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var previewView: PreviewView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
                startCamera()
            } else {
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(permissions,
                    REQUEST_CAMERA__PERMISSION
                )
                println("ダイアログ表示")
            }
        }
        binding = CameraFragmentBinding.inflate(layoutInflater, container, false)
        val root = inflater.inflate(R.layout.camera_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val context = this.requireContext()
        viewFinder = binding.root.findViewById<PreviewView>(R.id.viewFinder)
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

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this.requireContext())
        val executor = getMainExecutor(context)
        val listenerRunnable = Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindToLifecycle(cameraProvider)
        }
        cameraProviderFuture.addListener(listenerRunnable, executor)
    }
    fun bindToLifecycle(cameraProvider: ProcessCameraProvider){
        val preview: Preview = Preview.Builder().build()
        val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        preview.setSurfaceProvider(viewFinder.surfaceProvider)
        val camera = cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview)
    }

}



























































