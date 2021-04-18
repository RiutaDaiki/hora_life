package com.example.horalife.Example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.R
import com.example.horalife.databinding.ItemExampleRecyclerBinding

class ExampleFragment : Fragment() {
    private lateinit var recyclerView:RecyclerView
    private lateinit var exampleViewModel: ExampleFragmentViewModel
    private lateinit var adapter: RecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setRecyclerView()
        return inflater.inflate(R.layout.example_fragment, container, false)
    }

    private fun setRecyclerView(){
        val dataList = listOf<DataModel>(DataModel("調べ"), DataModel("乙"))
        recyclerView.layoutManager=LinearLayoutManager(context)
        adapter = RecyclerViewAdapter(dataList)
        recyclerView.adapter = adapter
    }

    }
