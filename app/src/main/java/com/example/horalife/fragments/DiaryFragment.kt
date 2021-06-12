package com.example.horalife.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horalife.R
import com.example.horalife.viewModel.DiaryViewModel
import com.example.horalife.databinding.DiaryFragmentBinding
import com.example.horalife.recycler.DiaryViewAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DiaryFragment : Fragment() {
    private lateinit var adapter: DiaryViewAdapter
    private lateinit var binding: DiaryFragmentBinding
    private val viewModel: DiaryViewModel by activityViewModels()
    private val user = Firebase.auth.currentUser
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DiaryFragmentBinding.inflate(layoutInflater, container, false)
        binding.diaryRecycler.layoutManager = LinearLayoutManager(context)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.view = this

        if (user != null) {
            adapter = DiaryViewAdapter(viewLifecycleOwner, viewModel, this.requireContext()) {
                viewModel.onClickRow(it)
                val action = DiaryFragmentDirections.actionDiaryToDiaryDetail()
                findNavController().navigate(action)
            }
            binding.diaryRecycler.adapter = adapter
            viewModel.setList{
                Toast.makeText(context, "日記の取得に失敗しました", Toast.LENGTH_SHORT).show()
            }
            viewModel.diaryList.observe(viewLifecycleOwner) {
                adapter.notifyDataSetChanged()
            }
        } else binding.noLoginTxt.text = resources.getString(R.string.no_login_diary)

        return binding.root
    }

    fun showEntries() {
        findNavController().navigate(R.id.action_nav_diary_to_entriesFragment)
    }

}
