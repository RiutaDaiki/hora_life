package com.example.horalife.diary

import android.graphics.Bitmap
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.*

class DiaryRepository {
    val ff = 0
    fun getDiaryInfo() {

        val db = Firebase.firestore
        db.collection("Diary items")
                .get()
                .addOnSuccessListener { result ->
                    var contentList = mutableListOf<DiaryContent>()

                    for (document in result) {
                        val d = document.data
                        val content = DiaryContent(d["recordedDate"].toString(), d["comment"].toString(), d["pngFileName"].toString())
                        contentList.add(content)
                    }

                }
    }

//    fun storeThumbnail(){
//        val baos = ByteArrayOutputStream()
//        thum?.compress(Bitmap.CompressFormat.PNG, 100, baos)
//        val data = baos.toByteArray()
//        val path = UUID.randomUUID().toString() + ".png"
//        val storageRef = Firebase.storage.reference
//        val uploadImageRef = storageRef.child("horanikki-thumbnail/$path")
//        uploadImageRef.putBytes(data)
//        val db = Firebase.firestore
//        val contents = DiaryContent(binding.dateText.text.toString(), binding.diaryText.text.toString(), path)
//        db.collection("Diary items")
//                .add(contents)
//    }
}