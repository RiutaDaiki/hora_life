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

class DiaryViewModel : ViewModel() {

    val diaryList = MutableLiveData<List<DiaryDetailContent>>()

    fun setList() {
        DiaryRepository().readDiaryInfo {
            diaryList.value = it
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
        DiaryRepository().deleteDiary(selectedDiary.value!!.diaryId.toString())
    }

    fun getVideoUri(uri: (Uri) -> Unit, fallBack: () -> Unit) {
        if (diaryList.value != null && selectedPosition.value != null) {
            DiaryRepository().readVideoUri(diaryList.value!!.get(selectedPosition.value!!).videoFileName) {
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

    fun getBitMap(position: Int, bitmap: (Bitmap?) -> Unit) {
        val storageRef = Firebase.storage.reference
        val thumbnailRef = storageRef.child("horanikki-thumbnail/${diaryList.value?.get(position)?.pngFileName}")
        val ONE_MEGABYTE: Long = 1024 * 1024
        thumbnailRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            bitmap(BitmapFactory.decodeByteArray(it, 0, it.size))
        }.addOnFailureListener {
            bitmap(null)
        }
    }
}
























