package com.example.horalife.diary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horalife.R
import com.example.horalife.databinding.DiaryFragmentBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DiaryFragment: Fragment() {
    private lateinit var adapter: DiaryViewAdapter
    private lateinit var binding: DiaryFragmentBinding
    private val viewModel: DiaryViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DiaryFragmentBinding.inflate(layoutInflater, container, false)
        binding.diaryRecycler.layoutManager = LinearLayoutManager(context)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.view = this

        val lam = {list: MutableList<DiaryContent> -> Unit}

       DiaryRepository().getDiaryInfo(lam)
        adapter = DiaryViewAdapter(viewLifecycleOwner, DiaryViewModel(), this.requireContext())
        binding.diaryRecycler.adapter = adapter

        viewModel.diaryList.observe(viewLifecycleOwner){
            //viewModelのdiaryListを監視して変化を感知したらnotifyDataSetChanged
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

     fun showEntries(){
        findNavController().navigate(R.id.action_nav_diary_to_entriesFragment)
    }

}