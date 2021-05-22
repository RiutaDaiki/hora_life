package com.example.horalife.diary

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
class DiaryDetailViewModel: ViewModel() {

    fun getVideoUri(videoString : String, uri: (Uri) -> Unit){
        val storageRef = Firebase.storage.reference
        storageRef.child("horanikki-video/$videoString").downloadUrl.addOnSuccessListener {
            uri(it)
        }
    }


}