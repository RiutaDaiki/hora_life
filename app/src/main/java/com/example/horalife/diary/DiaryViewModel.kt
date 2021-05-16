package com.example.horalife.diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiaryViewModel(
    diaryRepository: DiaryRepository = DiaryRepository()
) : ViewModel() {

    val diaryList = MutableLiveData<List<DiaryContent>>()

    init {
        diaryRepository.getDiaryInfo {
            diaryList.value = it
        }
    }
}
