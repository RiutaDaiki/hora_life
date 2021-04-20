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
        val root = inflater.inflate(R.layout.example_fragment, container, false)

        recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager=LinearLayoutManager(context)
//        val adapter = RecyclerViewAdapter(ExampleFragmentViewModel().dataList)
        recyclerView.adapter = RecyclerViewAdapter(ExampleFragmentViewModel().dataList)
//        setRecyclerView()
        return inflater.inflate(R.layout.example_fragment, container, false)
    }

    private fun setRecyclerView(){
        recyclerView.layoutManager=LinearLayoutManager(context)
        adapter = RecyclerViewAdapter(ExampleFragmentViewModel().dataList)
        recyclerView.adapter = adapter
    }
}