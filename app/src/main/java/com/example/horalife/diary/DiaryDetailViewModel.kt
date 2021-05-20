package com.example.horalife.diary

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
class DiaryDetailViewModel: ViewModel() {

    val videoName = MutableLiveData<String>()
    var stableVideoName : String? = null

        fun setVideoName(s: String){
            videoName.value = s
            stableVideoName = s
        }

    fun getVideoUri(videoString : String, uri: (Uri) -> Unit){
        val storageRef = Firebase.storage.reference
        Log.d("GET VIDEO URI", stableVideoName ?: "ういいい")
        storageRef.child("horanikki-video/$videoName").downloadUrl.addOnSuccessListener {
            uri(it)
        }
    }
}