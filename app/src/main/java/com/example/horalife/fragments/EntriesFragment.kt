package com.example.horalife.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.horalife.R
import com.example.horalife.databinding.EntriesFragmentBinding
import com.example.horalife.entity.DiaryContent
import com.example.horalife.viewModel.DiaryViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp


class EntrieFragment : Fragment() {
    private lateinit var thum: Bitmap
    private lateinit var videoPath: String
    private lateinit var videoUri: Uri
    private lateinit var binding: EntriesFragmentBinding
    private val viewModel: DiaryViewModel by activityViewModels()
    private val REQUEST_CODE = 1000

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (Build.VERSION.SDK_INT >= 23) {
            val permissions = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            )
            checkPermission(
                    permissions,
                    REQUEST_CODE
            )
        }

        binding = EntriesFragmentBinding.inflate(layoutInflater, container, false)
        binding.diaryBtn.isEnabled = false
        binding.lifecycleOwner = viewLifecycleOwner

        binding.camera.setOnClickListener {
            openVideoIntent()
        }

        binding.galleryBtn.setOnClickListener {
            openGallery()
        }

        binding.diaryBtn.setOnClickListener() {
            val date = (binding.datePicker.month + 1).toString() + " " + "/" + " " + binding.datePicker.dayOfMonth.toString()

            val contents = DiaryContent(
                    date,
                    binding.diaryText.text.toString(),
                    "",
                    Timestamp(System.currentTimeMillis()),
                    videoUri.lastPathSegment.toString(),
                    videoPath
            )
            viewModel.passEntries(thum, contents, videoUri)
            backToDiary()
        }

        return binding.root
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> for (i in 0..permissions.size) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_CODE -> {
                try {
                    resultData?.data?.also { uri ->
                        videoUri = uri
                        val columns: Array<String> = arrayOf(MediaStore.Video.Media.DATA)
                        val cursor = context?.contentResolver?.query(uri, columns, null, null, null)
                        cursor?.moveToFirst()
                        videoPath = cursor?.getString(0)!!
                        thum = ThumbnailUtils.createVideoThumbnail(
                                videoPath!!,
                                MediaStore.Video.Thumbnails.MINI_KIND
                        )!!
                        Log.d("path", videoPath)
                        Log.d("ビデオuri", uri.toString())
                        binding.diaryBtn.isEnabled = true
                        binding.thumbnailView.setImageBitmap(thum)
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "エラーが発生しました", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkPermission(permissions: Array<String>, request_code: Int) {
        ActivityCompat.requestPermissions(this.requireActivity(), permissions, request_code)
    }

    fun openVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(this.requireContext().packageManager).also {
                startActivityForResult(
                        takePictureIntent,
                        REQUEST_CODE
                )
            }
        }
    }

    fun openGallery() {
        val currentUser = Firebase.auth.currentUser

        if (currentUser != null) {
            Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            ).also { galleryIntent ->
                galleryIntent.resolveActivity(this.requireActivity().packageManager).also {
                    startActivityForResult(
                            galleryIntent,
                            REQUEST_CODE
                    )
                }
            }
        } else Toast.makeText(context, "ログインしていません", Toast.LENGTH_SHORT).show()

    }

    fun backToDiary() {
        findNavController().navigate(R.id.action_entries_to_diary)
    }
}


































