package com.example.horalife.diary

import android.os.Bundle
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

class DiaryFragment: Fragment() {
    private lateinit var adapter: DiaryViewAdapter
    private lateinit var binding: DiaryFragmentBinding
    private val viewModel: DiaryViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DiaryFragmentBinding.inflate(layoutInflater, container, false)
        binding.diaryRecycler.layoutManager = LinearLayoutManager(context)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.view = this

        adapter = DiaryViewAdapter(viewLifecycleOwner, viewModel, this.requireContext())
        binding.diaryRecycler.adapter = adapter

        viewModel.diaryList.observe(viewLifecycleOwner){
            adapter.notifyDataSetChanged()
            DiaryViewAdapter(viewLifecycleOwner, viewModel, requireContext())
        }
        viewModel.isRowClicked.observe(viewLifecycleOwner){
            // TODO  日記として表示するリストは既に降順担ってるのでリスト[it].mp4FileNameで動画を取得できるはず
            //なんやかんやしてsurfaceView1に表示する
            findNavController().navigate(R.id.action_diary_to_diary_detail)
            DiaryDetailViewModel().getVideoName(viewModel.diaryList.value!!.get(it).videoFileName)
        }
        return binding.root
    }

     fun showEntries(){
        findNavController().navigate(R.id.action_nav_diary_to_entriesFragment)
     }

}