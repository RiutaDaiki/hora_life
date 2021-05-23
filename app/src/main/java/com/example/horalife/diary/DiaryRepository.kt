package com.example.horalife.diary


import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp

class DiaryRepository {

    fun getDiaryInfo(list: (MutableList<DiaryContent>) -> Unit) {
        val db = Firebase.firestore

<<<<<<< HEAD
            db.collection("Diary items")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .get()

                    .addOnSuccessListener { result ->

                        val storingList = mutableListOf<DiaryContent>()

                        for (document in result) {
                            val d = document.data
                            val content = DiaryContent(d["recordedDate"].toString(), d["comment"].toString(),
                                    d["pngFileName"].toString(),
                                    Timestamp(System.currentTimeMillis()),
                                    d["videoFileName"].toString())
                            storingList.add(content)
                        }
                        list(storingList)
                    }
=======
        db.collection("Diary items")
            .get()
            .addOnSuccessListener { result ->
                val mList = mutableListOf<DiaryContent>()
                for (document in result) {
                    val d = document.data
                    val content = DiaryContent(
                        d["recordedDate"].toString(),
                        d["comment"].toString(),
                        d["pngFileName"].toString()
                    )
                    mList.add(content)
                    list(mList)

                }
            }


>>>>>>> 673b9c3e8e0cef065081476ea11ff83aa809d226
    }
}