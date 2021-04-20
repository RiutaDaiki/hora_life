package com.example.horalife.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.R
import com.example.horalife.databinding.ExampleFragmentBinding

class ExampleFragment : Fragment() {
    private lateinit var recyclerView:RecyclerView
    private lateinit var exampleViewModel: ExampleFragmentViewModel
    private lateinit var adapter: RecyclerViewAdapter
    private val MyViewModel: ExampleFragmentViewModel by viewModels<ExampleFragmentViewModel>()
    private lateinit var viewModel: ExampleFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ExampleFragmentBinding.inflate(layoutInflater, container, false)
        val root = inflater.inflate(R.layout.example_fragment, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerView.layoutManager=LinearLayoutManager(context)
        adapter = RecyclerViewAdapter(MyViewModel.dataList, viewLifecycleOwner, MyViewModel)
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //これ↓
        adapter.notifyDataSetChanged()

    }

}