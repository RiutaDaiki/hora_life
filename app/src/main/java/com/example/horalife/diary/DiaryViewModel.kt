package com.example.horalife.diary

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DiaryViewModel(diaryRepository: DiaryRepository = DiaryRepository()): ViewModel() {


    val diaryList = MutableLiveData<List<DiaryContent>>()

    init {
        diaryRepository.getDiaryInfo {
            diaryList.value = it
        }
    }

    fun diaryBitMap(position: Int, lamda: (Bitmap?) -> Unit){
        val storageRef = Firebase.storage.reference
        val thumbnailRef = storageRef.child("horanikki-thumbnail/${diaryList.value?.get(position)?.pngFileName}")
        val ONE_MEGABYTE: Long = 1024 * 1024
        thumbnailRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            lamda(BitmapFactory.decodeByteArray(it, 0, it.size))
        }.addOnFailureListener {
            lamda(null)
        }

    }

    val isRowClicked = MutableLiveData<Int>()

    fun onClickRow(rowPosition: Int) {
        isRowClicked.value = rowPosition
    }


}
