package com.example.horalife.diary

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.horalife.databinding.*
import com.example.horalife.diary_detail.DiaryDetailContent
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class DiaryViewModel() : ViewModel() {

    object Repository {
        val repository = DiaryRepository()
    }

    val diaryList = MutableLiveData<List<DiaryDetailContent>>()

    val currentAccount = MutableLiveData<FirebaseUser>()

    fun setList(fallBack: () -> Unit) {
        viewModelScope.launch {
            Repository.repository.readDiaryInfo(currentAccount.value)
                    .onSuccess {
                        diaryList.value = it
                    }
                    .onFailure {
                        fallBack
                    }
        }
    }

    val selectedPosition = MutableLiveData<Int>()
    val selectedDiary = selectedPosition.map {
        diaryList.value?.get(it)
    }

    fun onClickRow(position: Int) {
        selectedPosition.value = position
    }

    fun callDelete() {
        viewModelScope.launch {
            Repository.repository.deleteDiary(currentAccount.value, selectedDiary.value!!)
        }
    }

    fun getVideoUri(uri: (Uri) -> Unit, fallBack: () -> Unit) {
        viewModelScope.launch {
            Log.d("ビデオURえる", diaryList.value!!.get(selectedPosition.value!!).videoFileName.toUri().lastPathSegment!!)
            if (diaryList.value != null && selectedPosition.value != null) {
                Repository.repository.readVideoUri(diaryList.value!!.get(selectedPosition.value!!).videoFileName.toUri().lastPathSegment!!)
                        .onSuccess {
                            uri(it)
                        }
                        .onFailure { fallBack }
            } else {
                fallBack()
            }
        }
    }

    fun passEntries(thumb: Bitmap, content: DiaryContent, localVideo: Uri) {
        viewModelScope.launch {
            Repository.repository.createEntriesInfo(currentAccount.value, thumb, content, localVideo)
        }
    }

    fun getBitMap(position: Int, bitmap: (Bitmap?) -> Unit) {
        viewModelScope.launch {
            val pngFileName = diaryList.value?.get(position)?.pngFileName
            if (pngFileName != null) {
                Repository.repository.getByteArray(pngFileName)
                        .onSuccess {
                            bitmap(BitmapFactory.decodeByteArray(it, 0, it.size))
                        }
                        .onFailure { }
            }
        }
    }

}