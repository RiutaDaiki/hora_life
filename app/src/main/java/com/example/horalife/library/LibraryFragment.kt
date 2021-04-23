package com.example.horalife.library

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.horalife.MainActivity
import com.example.horalife.R
import com.example.horalife.databinding.LibraryFragmentBinding
import java.security.AccessController.getContext

class LibraryFragment : Fragment() {
    private val viewModel: LibraryViewModel by viewModels<LibraryViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = LibraryFragmentBinding.inflate(layoutInflater, container, false)
        val root = inflater.inflate(R.layout.library_fragment, container, false)

        binding.vm = LibraryViewModel(parentFragmentManager, this.requireContext())
        return binding.root
    }
}