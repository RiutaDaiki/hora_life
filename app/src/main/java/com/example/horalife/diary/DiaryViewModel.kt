package com.example.horalife.diary

import android.app.usage.UsageEvents
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.horalife.R
import com.google.firebase.events.Event
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class DiaryViewModel(diaryRepository: DiaryRepository = DiaryRepository()): ViewModel() {


    val diaryList = MutableLiveData<List<DiaryContent>>()
    val bitmap = MutableLiveData<Bitmap?>()

    //ここでストレージの参照を作るのはなんだかスマートじゃない気がする
    private fun createRef(storageRef: StorageReference, position: Int): StorageReference{
        return storageRef.child("horanikki-thumbnail/${diaryList.value?.get(position)?.pngFileName}")
    }




    init {
        diaryRepository.getDiaryInfo {
            diaryList.value = it
        }
        diaryRepository.diaryBitMap({ bitmap.value = it  }, createRef())
    }

    val isRowClicked = MutableLiveData<Boolean>()

    fun onClickRow() {
        isRowClicked.value = true
    }


}
