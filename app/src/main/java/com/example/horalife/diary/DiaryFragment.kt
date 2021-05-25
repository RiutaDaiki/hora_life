package com.example.horalife.diary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horalife.R
import com.example.horalife.databinding.DiaryFragmentBinding
import java.lang.IllegalArgumentException

class DiaryFragment: Fragment() {
    private lateinit var adapter: DiaryViewAdapter
    private lateinit var binding: DiaryFragmentBinding
    private val viewModel: DiaryViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DiaryFragmentBinding.inflate(layoutInflater, container, false)
        binding.diaryRecycler.layoutManager = LinearLayoutManager(context)
        binding.lifecycleOwner = viewLifecycleOwner
        //↓日記画面のfabはこのクラスのshowEntries()を起動します。diary_fragment.xmlでandroid:onClick="@{() -> view.showEntries()}"としているので、この記述は必要だと思います。
        binding.view = this

        adapter = DiaryViewAdapter(viewLifecycleOwner, viewModel, this.requireContext()) {
            viewModel.onClickRow(it)
            val action = DiaryFragmentDirections.actionDiaryToDiaryDetail()
            findNavController().navigate(action)
        }
        binding.diaryRecycler.adapter = adapter

        viewModel.diaryList.observe(viewLifecycleOwner){
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

     fun showEntries(){
        findNavController().navigate(R.id.action_nav_diary_to_entriesFragment)
     }

}
