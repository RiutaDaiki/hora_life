package com.example.horalife.library

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraManager
import android.media.ImageReader
import android.util.Log
import android.view.TextureView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class LibraryViewModel(private val supportFragmentManager: FragmentManager, private val requireContext: Context): ViewModel() {
private var imageReader: ImageReader? = null


    fun showDialog() {
        val dialog = SelectDialogFragment()
        dialog.show(supportFragmentManager, null)
        Log.d("j", "ダイアログ")
    }

    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {

        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 2)
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
            TODO("Not yet implemented")
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
            TODO("Not yet implemented")
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            TODO("Not yet implemented")
        }
    }

    fun openCamera() {

        var manager: CameraManager = getSystemService(requireContext, Context.CAMERA_SERVICE) as CameraManager

        try {
            var camerId: String = manager.getCameraIdList()[0]
            val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermission()
                return
            }
            manager.openCamera(camerId, stateCallback, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}