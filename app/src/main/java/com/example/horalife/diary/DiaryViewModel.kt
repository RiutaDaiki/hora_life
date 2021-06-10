package com.example.horalife.diary

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.horalife.databinding.EntriesFragmentBinding
import com.example.horalife.diary_detail.DiaryDetailContent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class DiaryViewModel() : ViewModel() {

    object Repository {
        val repository = DiaryRepository()
    }





    val diaryList = MutableLiveData<List<DiaryDetailContent>>()

    val currentAccount = MutableLiveData<FirebaseUser>()

    fun setList(callBack :() -> Unit) {
        viewModelScope.launch {

            Repository.repository.readDiaryInfo(currentAccount.value)
                    .onSuccess {
                        diaryList.value =  it
                    }
                    .onFailure {

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
        viewModelScope.launch{
            Repository.repository.deleteDiary(currentAccount.value, selectedDiary.value!!)
        }
    }


    fun getVideoUri(uri: (Uri) -> Unit, fallBack: () -> Unit) {
        viewModelScope.launch {
        if (diaryList.value != null && selectedPosition.value != null) {
            Repository.repository.readVideoUri(diaryList.value!!.get(selectedPosition.value!!).videoFileName) {
                uri(it)
            }
        } else {
            fallBack()
        }
        }
    }

    fun passEntries(thum: Bitmap, localVideo: Uri, binding: EntriesFragmentBinding, videoPath: String) {
        viewModelScope.launch {
            Repository.repository.createEntriesInfo(currentAccount.value, thum, localVideo, binding, videoPath)
        }
    }

    fun getBitMap(position: Int, bitmap: (Bitmap?) -> Unit) {
        viewModelScope.launch {


            val storageRef = Firebase.storage.reference
            val thumbnailRef =
                    storageRef.child("horanikki-thumbnail/${diaryList.value?.get(position)?.pngFileName}")
            val ONE_MEGABYTE: Long = 1024 * 1024
            thumbnailRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                bitmap(BitmapFactory.decodeByteArray(it, 0, it.size))
            }.addOnFailureListener {
                bitmap(null)
            }
        }
    }

}