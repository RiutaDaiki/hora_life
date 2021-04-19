package com.example.horalife.Example

import androidx.lifecycle.ViewModel

class ExampleFragmentViewModel(dataList: List<DataModel>): ViewModel() {
    val soundText = "ウホーイ"
    val list: List<DataModel> = listOf(DataModel("9"))
    //modelを参照する：ExampleFragmentを呼ぶ
    val model = ExampleFragment()

}