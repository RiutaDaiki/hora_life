package com.example.horalife.diary

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.horalife.databinding.EntriesFragmentBinding
import com.example.horalife.diary_detail.DiaryDetailContent
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DiaryViewModel(diaryRepository: DiaryRepository = DiaryRepository()) : ViewModel() {

    val diaryList = MutableLiveData<List<DiaryDetailContent>>()

    fun setList() {
        DiaryRepository().readDiaryInfo {
            diaryList.value = it
        }
    }

    fun diaryBitMap(position: Int, lamda: (Bitmap?) -> Unit) {
        val storageRef = Firebase.storage.reference
        val thumbnailRef = storageRef.child("horanikki-thumbnail/${diaryList.value?.get(position)?.pngFileName}")
        val ONE_MEGABYTE: Long = 1024 * 1024
        thumbnailRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            lamda(BitmapFactory.decodeByteArray(it, 0, it.size))
        }.addOnFailureListener {
            lamda(null)
        }

    }


    private val selectedPosition = MutableLiveData<Int>()
    val selectedDiary = selectedPosition.map {
        diaryList.value?.get(it)
    }

    fun onClickRow(position: Int) {
        selectedPosition.value = position
    }

    fun deleteDocument() {
        DiaryRepository().deleteDiary(selectedDiary.value!!.diaryId.toString())
    }

    fun getVideoUri(uri: (Uri) -> Unit, fallBack: () -> Unit) {
        val storageRef = Firebase.storage.reference
        if (diaryList.value != null && selectedPosition.value != null) {
            storageRef.child("horanikki-video/${diaryList.value!!.get(selectedPosition.value!!).videoFileName}").downloadUrl.addOnSuccessListener {
                uri(it)
            }
        } else {
            fallBack()
        }

    }

    fun passEntries(thum: Bitmap, localVideo: Uri, binding: EntriesFragmentBinding) {
        DiaryRepository().createEntriesInfo(thum, localVideo, binding)
        setList()
    }


}
























