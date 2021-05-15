package com.example.horalife.diary

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

class DiaryRepository {

    fun getDiaryInfo(list : (MutableList<DiaryContent>) -> Unit){
        val db = Firebase.firestore

            db.collection("Diary items")
                    .get()
                    .addOnSuccessListener { result ->

                        val mList = mutableListOf<DiaryContent>()
                        for (document in result) {
                            val d = document.data
                            val content = DiaryContent(d["recordedDate"].toString(), d["comment"].toString(), d["pngFileName"].toString())
                            mList.add(content)
                            list(mList)

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