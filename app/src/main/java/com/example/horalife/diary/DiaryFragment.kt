package com.example.horalife.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horalife.R
import com.example.horalife.databinding.DiaryFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DiaryFragment: Fragment() {
    private lateinit var adapter: DiaryViewAdapter
    private lateinit var binding: DiaryFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DiaryFragmentBinding.inflate(layoutInflater, container, false)
        val fab = binding.root.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener(){
            showEntries()
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.diaryRecycler.layoutManager = LinearLayoutManager(context)
        adapter = DiaryViewAdapter(viewLifecycleOwner)
        binding.diaryRecycler.adapter = adapter

        return binding.root
    }
    private fun showEntries(){
        val entries = EntrieFragment()
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, entries)
        fragmentTransaction.commit()
    }
}