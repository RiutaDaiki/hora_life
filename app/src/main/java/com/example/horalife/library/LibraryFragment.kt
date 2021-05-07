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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horalife.MainActivity
import com.example.horalife.R
import com.example.horalife.databinding.LibraryFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.security.AccessController.getContext

class LibraryFragment : Fragment() {
    private val viewModel: LibraryViewModel by viewModels<LibraryViewModel>()
    private var imageReader: ImageReader? = null
    private lateinit var adapter : LibraryViewAdapter

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = LibraryFragmentBinding.inflate(layoutInflater, container, false)
        val root = inflater.inflate(R.layout.library_fragment, container, false)
            binding.lifecycleOwner = viewLifecycleOwner
            binding.libraryRecyclerView.layoutManager = LinearLayoutManager(context)
            adapter = LibraryViewAdapter(viewLifecycleOwner)
            binding.libraryRecyclerView.adapter = adapter

        val fab = binding.root.findViewById<FloatingActionButton>(R.id.float_btn)
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

}