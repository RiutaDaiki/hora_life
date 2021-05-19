package com.example.horalife.diary

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.horalife.databinding.EntriesFragmentBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.sql.Timestamp
import java.util.*

class EntriesViewModel: ViewModel() {

    fun storeEntriesInfo(thum: Bitmap, localVideo: Uri, binding: EntriesFragmentBinding){
        val baos = ByteArrayOutputStream()
        thum?.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        val path = UUID.randomUUID().toString() + ".png"
        val storageRef = Firebase.storage.reference
        val uploadImageRef = storageRef.child("horanikki-thumbnail/$path")
        uploadImageRef.putBytes(data)
        val uploadVideoRef = storageRef.child("horanikki-video/${localVideo.lastPathSegment}")
        uploadVideoRef.putFile(localVideo)
        val db = Firebase.firestore
        val contents = DiaryContent(binding.dateText.text.toString(), binding.diaryText.text.toString(), path, Timestamp(System.currentTimeMillis()), localVideo.lastPathSegment.toString())
        db.collection("Diary items")
                .add(contents)
    }
}