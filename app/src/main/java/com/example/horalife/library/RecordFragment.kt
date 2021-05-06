package com.example.horalife.library

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
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.horalife.R
import com.example.horalife.databinding.RecordFragmentBinding
import android.Manifest
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200


class RecordFragment: Fragment() {

    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている

            } else {
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(permissions, REQUEST_RECORD_AUDIO_PERMISSION)
                println("ダイアログ表示")
            }
        }

        dispatchRecordIntent()


        return binding.root
    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }

    private fun backLibrary(){
        val libraryFragment = LibraryFragment()
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.fragment_container, libraryFragment)
        fragmentTransaction.commit()
    }

    fun dispatchRecordIntent() {
        Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION).also { takePictureIntent ->
            takePictureIntent.resolveActivity(this.requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_RECORD_AUDIO_PERMISSION)
            }
        }
    }

}



































