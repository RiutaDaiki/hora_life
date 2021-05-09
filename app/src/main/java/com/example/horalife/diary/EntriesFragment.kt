 package com.example.horalife.diary

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.horalife.MainActivity
import com.example.horalife.R
import com.example.horalife.camera.CameraFragment
import com.example.horalife.databinding.EntriesFragmentBinding
import com.example.horalife.library.LibraryFragment
import java.time.LocalDate
 private val REQUEST_CODE = 1000

class EntrieFragment: Fragment() {
    lateinit var recordWay: String
    lateinit var binding: EntriesFragmentBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = EntriesFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.diaryCancelBtn.setOnClickListener(){
            showCancelConfirm()
        }

        if(Build.VERSION.SDK_INT >= 23){
            val permissions = arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            )
            checkPermission(permissions, REQUEST_CODE)
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
        binding.diaryBtn.setOnClickListener(){
            openGallery()
        }
//        binding.thumbnailView.setImageBitmap()
//        val e : Bitmap = ThumbnailUtils.createVideoThumbnail()

        return binding.root
    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE -> for (i in 0..permissions.size){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context, "パーミッションクリア", Toast.LENGTH_SHORT)
                } else {
                    Toast.makeText(context, "パーミッションフェイルど", Toast.LENGTH_SHORT)

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
                        val inputStream = context?.contentResolver?.openInputStream(uri)
                        val image = BitmapFactory.decodeStream(inputStream)
                        val columns: Array<String> = arrayOf(MediaStore.Video.Media.DATA)
                        val cursor = context?.contentResolver?.query(uri, columns, null, null, null)
                        cursor?.moveToFirst()
                        val path = cursor?.getString(0)
                        Log.d("ここーーーーーーーーーーーーーーーーーーーーー", path!!)
                        Log.d("", "うんこ")
                        val thum = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND)
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


































