package com.example.horalife.Example

import androidx.lifecycle.ViewModel
import com.example.horalife.R
import com.example.horalife.Sound

class ExampleFragmentViewModel(): ViewModel() {
    val soundText = "u"
    val dataList = listOf<Sound>(Sound(R.string.sirabe.toString()), Sound(R.string.otsu.toString()))
    //modelを参照する：ExampleFragmentを呼ぶ
    val model = ExampleFragment()

}