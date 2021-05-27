package com.example.horalife.diary


import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.horalife.databinding.EntriesFragmentBinding
import com.example.horalife.diary_detail.DiaryDetailContent
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.sql.Timestamp
import java.util.*

class DiaryRepository {
    private val db = Firebase.firestore
    private val storageRef = Firebase.storage.reference


    fun createEntriesInfo(thum: Bitmap, localVideo: Uri, binding: EntriesFragmentBinding) {
        val baos = ByteArrayOutputStream()
        thum?.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        val path = UUID.randomUUID().toString() + ".png"
        val uploadImageRef = storageRef.child("horanikki-thumbnail/$path")
        uploadImageRef.putBytes(data)
        val uploadVideoRef = storageRef.child("horanikki-video/${localVideo.lastPathSegment}")
        uploadVideoRef.putFile(localVideo)
        val contents = DiaryContent(
                binding.dateText.text.toString(),
                binding.diaryText.text.toString(),
                path,
                Timestamp(System.currentTimeMillis()),
                localVideo.lastPathSegment.toString()
        )
        db.collection("Diary items")
                .add(contents)

    }


    fun readDiaryInfo(list: (MutableList<DiaryDetailContent>) -> Unit) {

        db.collection("Diary items")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()

                .addOnSuccessListener { result ->

                    val storingList = mutableListOf<DiaryDetailContent>()

                    for (document in result) {
                        val d = document.data
                        Log.d("id", document.id)
                        val content = DiaryDetailContent(
                                document.id,
                                d["recordedDate"].toString(),
                                d["comment"].toString(),
                                d["pngFileName"].toString(),
                                d["videoFileName"].toString())
                        storingList.add(content)
                    }
                    list(storingList)
                }
    }

    fun updateDiary() {

    }

    fun deleteDiary(id: String) {
        db.collection("Diary items").document(id)
                .delete()
    }

    fun readVideoUri(videoFileName: String, uri: (Uri) -> Unit) {
        storageRef.child("horanikki-video/$videoFileName").downloadUrl.addOnSuccessListener {
            uri(it)
        }

    }

}