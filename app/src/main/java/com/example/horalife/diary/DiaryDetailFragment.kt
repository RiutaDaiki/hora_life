package com.example.horalife.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.horalife.databinding.DiaryDetailBinding
import com.example.horalife.databinding.DiaryFragmentBinding

class DiaryDetailFragment: Fragment() {
    private lateinit var binding: DiaryDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DiaryDetailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}