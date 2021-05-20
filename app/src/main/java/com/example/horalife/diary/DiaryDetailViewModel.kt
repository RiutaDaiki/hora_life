package com.example.horalife.diary

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DiaryDetailViewModel: ViewModel() {

    val videoName = MutableLiveData<String>()



        fun getVideoName(s: String){
            videoName.value = s
            Log.d("ビデオネーム", s)
        }


    fun getVideoUri(videoName : String, uri: (Uri) -> Unit){
        val storageRef = Firebase.storage.reference
        storageRef.child("horanikki-video/$videoName").downloadUrl.addOnSuccessListener {
            uri(it)
        }
    }
}