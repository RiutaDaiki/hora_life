package com.example.horalife.diary

import android.app.usage.UsageEvents
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.events.Event
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DiaryViewModel(diaryRepository: DiaryRepository = DiaryRepository()): ViewModel() {


    val diaryList = MutableLiveData<List<DiaryContent>>()

    init {
        diaryRepository.getDiaryInfo {
            diaryList.value = it
        }
    }

    val isRowClicked = MutableLiveData<Boolean>()

    fun onClickRow() {
        isRowClicked.value = true
    }
}
