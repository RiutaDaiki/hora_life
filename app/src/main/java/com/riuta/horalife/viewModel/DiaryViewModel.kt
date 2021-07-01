package com.riuta.horalife.viewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.riuta.horalife.dataClass.DiaryContent
import com.riuta.horalife.model.DiaryRepository
import com.riuta.horalife.dataClass.DiaryDetailContent
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import java.sql.Timestamp


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

    val content = MutableLiveData<List<DiaryContent>>(diaryContent())

    fun diaryContent(): List<DiaryContent>{

        setList {  }
        Log.d("debug", diaryList.value?.get(0)?.recordedDate ?: "ぬるぽ")
        val list = mutableListOf<DiaryContent>()
        val rowNumber = diaryList.value?.size ?: 0
        for(i in 0 until rowNumber){
            val comment = diaryList.value?.get(i)?.comment ?: ""
            val rowComment = if (comment.length < 40) comment else comment.substring(0..41)
            val rowContent = DiaryContent(
                diaryList.value?.get(i)?.recordedDate ?: "",
                rowComment,
                diaryList.value?.get(i)?.pngFileName ?: "",
                Timestamp(System.currentTimeMillis()),
                diaryList.value?.get(i)?.videoFileName ?: "",
                diaryList.value?.get(i)?.videoPath ?: ""
            )
            list.add(i, rowContent)
        }
        val result: List<DiaryContent> = list
        return result
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
            }
        }
    }

}