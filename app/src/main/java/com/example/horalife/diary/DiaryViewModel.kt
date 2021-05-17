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
}
