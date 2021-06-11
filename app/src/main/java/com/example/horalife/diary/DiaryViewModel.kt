package com.example.horalife.diary

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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

    fun setList(fallBack :() -> Unit) {
        viewModelScope.launch {
            Repository.repository.readDiaryInfo(currentAccount.value)
                    .onSuccess {
                        diaryList.value =  it
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
        viewModelScope.launch{
            Repository.repository.deleteDiary(currentAccount.value, selectedDiary.value!!)
        }
    }

    fun getVideoUri(binding: DiaryDetailBinding, fallBack: () -> Unit){
        viewModelScope.launch {
            if (diaryList.value != null && selectedPosition.value != null) {
                Repository.repository.readVideoUri(diaryList.value!!.get(selectedPosition.value!!).videoFileName)
                        .onSuccess {
                            val monitor = binding.videoView
                            monitor.setVideoURI(it)
                            monitor.setOnPreparedListener {
                                binding.videoProgressBar.visibility = android.widget.ProgressBar.INVISIBLE
                                binding.thumView.visibility = android.widget.ImageView.INVISIBLE
                                monitor.start()
                            }
                        }
                        .onFailure { fallBack }
            } else {
                fallBack()
            }
        }
    }

    fun passEntries(thum: Bitmap, content: DiaryContent, navToDiary: () -> Unit) {
        viewModelScope.launch {
            Repository.repository.createEntriesInfo(currentAccount.value, thum, content)
        }
        navToDiary
        setList { println("bug") }
    }

    fun getBitMap(position: Int, bitmap: (Bitmap?) -> Unit){
        viewModelScope.launch {
            getByteArray(position)
                    .onSuccess {
                        bitmap(BitmapFactory.decodeByteArray(it, 0, it.size))
                    }
                    .onFailure {  }
        }
    }

    suspend fun getByteArray(position: Int): Result<ByteArray> {
        return kotlin.runCatching {
            suspendCoroutine { continuation ->
                val storageRef = Firebase.storage.reference
                val thumbnailRef =
                        storageRef.child("horanikki-thumbnail/${diaryList.value?.get(position)?.pngFileName}")
                val ONE_MEGABYTE: Long = 1024 * 1024
                thumbnailRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                    continuation.resume(it)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
            }
        }
    }

}