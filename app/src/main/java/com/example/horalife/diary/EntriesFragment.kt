 package com.example.horalife.diary

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.horalife.R
import com.example.horalife.databinding.EntriesFragmentBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.util.*

 private val REQUEST_CODE = 1000

class EntrieFragment: Fragment() {
    var thum : Bitmap? = null
    lateinit var recordWay: String
    lateinit var binding: EntriesFragmentBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        if(Build.VERSION.SDK_INT >= 23){
            val permissions = arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            )
            checkPermission(permissions, REQUEST_CODE)
        }

        binding = EntriesFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.diaryCancelBtn.setOnClickListener(){
            showCancelConfirm()
        }
        binding.camera.setOnClickListener(){
            openVideoIntent()
            recordWay = "動画"
            binding.recordWayText.text = recordWay + " " + ":"

        }
        binding.mic.setOnClickListener(){
            openMicIntent()
            recordWay = "音声"
            binding.recordWayText.text = recordWay + " " + ":"
        }
        binding.dateText.setText(LocalDate.now().toString())
        binding.galleryBtn.setOnClickListener(){
            openGallery()
        }
        binding.diaryBtn.setOnClickListener(){
            val baos = ByteArrayOutputStream()
            thum?.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val data = baos.toByteArray()
            val path = UUID.randomUUID().toString() + ".png"
            val storageRef = Firebase.storage.reference
            val uploadImageRef = storageRef.child("horanikki-thumbnail/$path")
            uploadImageRef.putBytes(data)
            val db = Firebase.firestore
            val contents = DiaryContent(binding.dateText.text.toString(), binding.diaryText.text.toString(), path)
            db.collection("Diary items")
                    .add(contents)
        }

        return binding.root
    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE -> for (i in 0..permissions.size){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
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
                        val columns: Array<String> = arrayOf(MediaStore.Video.Media.DATA)
                        val cursor = context?.contentResolver?.query(uri, columns, null, null, null)
                        cursor?.moveToFirst()
                        val path = cursor?.getString(0)

                        thum = ThumbnailUtils.createVideoThumbnail(path!!, MediaStore.Video.Thumbnails.MINI_KIND)
                        val imageView = binding.root.findViewById<ImageView>(R.id.thumbnail_view)

                        imageView.setImageBitmap(thum)
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "エラーが発生しました", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showCancelConfirm(){
        val dialog = CancelDialogFragment()
        dialog.show(parentFragmentManager, null)
    }
    private fun checkPermission(permissions: Array<String>, request_code: Int){
        ActivityCompat.requestPermissions(this.requireActivity(), permissions, request_code)
    }
    fun openVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(this.requireContext().packageManager).also {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            }
        }
    }
    fun openMicIntent() {
        Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION).also { takeSoundIntent ->
            takeSoundIntent.resolveActivity(this.requireContext().packageManager).also {
                startActivityForResult(takeSoundIntent, REQUEST_CODE)
            }
        }
    }
    private fun openGallery(){
        Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI).also { galleryIntent ->
            galleryIntent.resolveActivity(this.requireActivity().packageManager).also {
                startActivityForResult(galleryIntent, REQUEST_CODE)
            }
        }
    }
}


































