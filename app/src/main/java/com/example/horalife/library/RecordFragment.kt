package com.example.horalife.library

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.horalife.MainActivity
import com.example.horalife.R
import com.example.horalife.databinding.RecordFragmentBinding
import java.io.File

class RecordFragment: Fragment() {

    private lateinit var recordViewModel: RecordViewModel
    private val MyViewModel : RecordViewModel by viewModels<RecordViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = RecordFragmentBinding.inflate(layoutInflater, container, false)
        val root = inflater.inflate(R.layout.record_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val backBtn = binding.root.findViewById<Button>(R.id.back_button)
        val recordBtn = binding.root.findViewById<Button>(R.id.record_button)

        backBtn?.setOnClickListener{
            Log.d("うんこ", "じ")
            backLibrary()
//            parentFragmentManager.popBackStack()
        }


        return binding.root
    }

    private fun backLibrary(){
        val libraryFragment = LibraryFragment()
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.fragment_container, libraryFragment)
        fragmentTransaction.commit()
    }

}