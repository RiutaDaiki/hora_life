package com.example.horalife.diary

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
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

                        }
                        list(mList)
                    }
    }

    fun diaryBitMap(lamda: (Bitmap?) -> Unit, thumbnailRef: StorageReference){
        val storageRef = Firebase.storage.reference
        //
        val ONE_MEGABYTE: Long = 1024 * 1024
        thumbnailRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {

            lamda(BitmapFactory.decodeByteArray(it, 0, it.size))
        }.addOnFailureListener {
            lamda(null)
        }
    }

}