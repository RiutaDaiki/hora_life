package com.example.horalife.diary_entries

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.horalife.R
import com.example.horalife.databinding.EntriesFragmentBinding
import com.example.horalife.diary.DiaryViewModel
import java.time.LocalDate

private val REQUEST_CODE = 1000

class EntrieFragment : Fragment() {
    lateinit var thum: Bitmap
    lateinit var path: String
    lateinit var videoUri: Uri
    lateinit var binding: EntriesFragmentBinding
    private val viewModel: DiaryViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        if (Build.VERSION.SDK_INT >= 23) {
            val permissions = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            )
            checkPermission(permissions,
                    REQUEST_CODE
            )
        }

        val noImageDrawable = ContextCompat.getDrawable(this.requireContext(), R.drawable.no_image)
        val bitmapDrawable = noImageDrawable as BitmapDrawable
        thum = bitmapDrawable.bitmap
        binding = EntriesFragmentBinding.inflate(layoutInflater, container, false)
        binding.diaryBtn.isEnabled = false
        binding.lifecycleOwner = viewLifecycleOwner
        binding.view = this

        binding.dateText.setText(LocalDate.now().toString())

        binding.diaryBtn.setOnClickListener() {
            viewModel.passEntries(thum, videoUri, binding)
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
                        path = cursor?.getString(0)!!
                        thum = ThumbnailUtils.createVideoThumbnail(path!!, MediaStore.Video.Thumbnails.MINI_KIND)!!
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
                startActivityForResult(takePictureIntent,
                        REQUEST_CODE
                )
            }
        }
    }

    fun openGallery() {
        Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI).also { galleryIntent ->
            galleryIntent.resolveActivity(this.requireActivity().packageManager).also {
                startActivityForResult(galleryIntent,
                        REQUEST_CODE
                )
            }
        }
    }

    fun backToDiary() {
        findNavController().navigate(R.id.action_entries_to_diary)
    }
}


































