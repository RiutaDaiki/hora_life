package com.example.horalife.library

import android.media.ImageReader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.horalife.R
import com.example.horalife.databinding.LibraryFragmentBinding
import com.example.horalife.diary.DiaryFragment

class LibraryFragment : Fragment() {
    private val viewModel: LibraryViewModel by viewModels<LibraryViewModel>()
    private var imageReader: ImageReader? = null
//    private lateinit var adapter : LibraryViewAdapter

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = LibraryFragmentBinding.inflate(layoutInflater, container, false)
        val root = inflater.inflate(R.layout.library_fragment, container, false)
            binding.lifecycleOwner = viewLifecycleOwner

            val diary = DiaryFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, diary)
            transaction.commit()

        binding.vm = LibraryViewModel()
        return binding.root
    }
}