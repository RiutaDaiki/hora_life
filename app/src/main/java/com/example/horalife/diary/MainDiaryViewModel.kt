package com.example.horalife.diary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainDiaryViewModel(application: Application): AndroidViewModel(application) {
    val recordModels = MutableLiveData<List<DiaryViewModel>>()

}