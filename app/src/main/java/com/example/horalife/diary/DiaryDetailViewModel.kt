package com.example.horalife.diary

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
class DiaryDetailViewModel: ViewModel() {

    val videoName = MutableLiveData<String>()

        fun setVideoName(s: String){
            videoName.value = s
        }

    fun getVideoUri(videoName : String, uri: (Uri) -> Unit){
        val storageRef = Firebase.storage.reference
        storageRef.child("horanikki-video/$videoName").downloadUrl.addOnSuccessListener {
            uri(it)
        }
    }
}