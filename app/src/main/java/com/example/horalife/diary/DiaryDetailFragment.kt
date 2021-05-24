package com.example.horalife.diary

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.example.horalife.databinding.DiaryDetailBinding

class DiaryDetailFragment(): Fragment() {
    private val args: DiaryDetailFragmentArgs by navArgs()
    private lateinit var binding: DiaryDetailBinding
    private val viewmodel : DiaryDetailViewModel by viewModels()
    private lateinit var rowVideoUri: Uri


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DiaryDetailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val monitor = binding.videoView
        binding.videoProgressBar.visibility = android.widget.ProgressBar.INVISIBLE

        binding.playBtn.setOnClickListener(){
            binding.videoProgressBar.visibility = android.widget.ProgressBar.VISIBLE
            val rowVideoName = args.videoFileName
            viewmodel.getVideoUri(rowVideoName) {
                rowVideoUri = it
                monitor.setVideoPath(it.toString())
                monitor.setOnPreparedListener {
                    binding.videoProgressBar.visibility = android.widget.ProgressBar.INVISIBLE
                    monitor.start()
                }
            }
        }

        return binding.root
    }


}