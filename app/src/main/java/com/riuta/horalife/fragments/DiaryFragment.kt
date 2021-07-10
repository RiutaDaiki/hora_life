package com.riuta.horalife.fragments

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
import com.riuta.horalife.R
import com.riuta.horalife.viewModel.DiaryViewModel
import com.riuta.horalife.databinding.DiaryFragmentBinding
import com.riuta.horalife.recycler.DiaryViewAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DiaryFragment : Fragment() {
    private val viewModel: DiaryViewModel by activityViewModels()
    private val user = Firebase.auth.currentUser
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DiaryFragmentBinding.inflate(layoutInflater, container, false)
        binding.diaryRecycler.layoutManager = LinearLayoutManager(context)
        binding.lifecycleOwner = viewLifecycleOwner

        if (user != null) {
            binding.enable = true
            val adapter = DiaryViewAdapter(viewLifecycleOwner, viewModel, this.requireContext()) {
                viewModel.onClickRow(it)
                val action = DiaryFragmentDirections.actionDiaryToDiaryDetail()
                findNavController().navigate(action)
            }
            binding.diaryRecycler.adapter = adapter
            viewModel.setList {
                Toast.makeText(context, "日記の取得に失敗しました", Toast.LENGTH_SHORT).show()
            }
            viewModel.diaryList.observe(viewLifecycleOwner) {
                adapter.notifyDataSetChanged()
            }
        } else {
            binding.noLoginTxt.text = resources.getString(R.string.no_login_diary)
            binding.enable = false
        }

        binding.fab.setOnClickListener { showEntries() }

        return binding.root
    }

    fun showEntries() {
        findNavController().navigate(R.id.action_nav_diary_to_entriesFragment)
    }

}
