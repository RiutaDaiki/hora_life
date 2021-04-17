package com.example.horalife.Example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.R
import com.example.horalife.databinding.ExampleFragmentBinding

class ExampleFragment : Fragment() {
    private lateinit var binding: ExampleFragmentBinding
    private lateinit var manager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val data = listOf(DataModel("調べ"), DataModel("乙"))
        binding = ExampleFragmentBinding.inflate(layoutInflater)
        manager = LinearLayoutManager(context)
        binding.recyclerView.apply {
            adapter = RecyclerViewAdapter(data)
            layoutManager = manager
        }
        return inflater.inflate(R.layout.example_fragment, container, false)
    }
}