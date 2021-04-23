package com.example.horalife.library

import android.Manifest
import android.content.Context
import android.content.Context.CAMERA_SERVICE
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraManager
import android.media.ImageReader
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.horalife.MainActivity
import com.example.horalife.R
import com.example.horalife.databinding.LibraryFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.security.AccessController.getContext

class LibraryFragment : Fragment() {
    private val viewModel: LibraryViewModel by viewModels<LibraryViewModel>()
    private var imageReader: ImageReader? = null

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = LibraryFragmentBinding.inflate(layoutInflater, container, false)
        val root = inflater.inflate(R.layout.library_fragment, container, false)

        val fab = root.findViewById<FloatingActionButton>(R.id.float_btn)
        fab.setOnClickListener(){
            showDialog()
        }

        binding.vm = LibraryViewModel()
        return binding.root
    }
    
    fun showDialog() {
        val dialog = SelectDialogFragment()
        dialog.show(parentFragmentManager, null)
        Log.d("j", "ダイアログ")
    }

    fun openCamera() {

    var manager: CameraManager = getSystemService(requireContext(),  Context.CAMERA_SERVICE) as CameraManager

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
}

