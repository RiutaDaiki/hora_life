package com.example.horalife.example

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.horalife.R
import com.example.horalife.Sound

class ExampleFragmentViewModel(): ViewModel() {
    val dataList = listOf<Sound>(Sound(R.string.sirabe), Sound(R.string.otsu), Sound(R.string.kan), Sound(R.string.yuri), Sound(R.string.tome))

    fun onClick(soundFile: Int){
        //音流す
//        print("音流れてる")
        Log.d("i", "o")

    }
}