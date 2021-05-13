package com.example.horalife.diary

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DiaryViewModel(private val path: String): ViewModel() {

   val value = displayThumbnail(path)
    fun displayThumbnail(currentPath: String): Bitmap?{
        val storageRef = Firebase.storage.reference
        val thumbnailRef = storageRef.child("horanikki-thumbnail/$currentPath")
        val ONE_MEGABYTE: Long = 1024 * 1024
        var bitmap : Bitmap?= null
        val byteArray =  thumbnailRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
        }
        println(bitmap)
        return bitmap
    }
}
