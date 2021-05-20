package com.example.horalife.diary

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.horalife.databinding.DiaryDetailBinding
import com.example.horalife.databinding.DiaryFragmentBinding

class DiaryDetailFragment(): Fragment() {
    private lateinit var binding: DiaryDetailBinding
    private lateinit var videoPath: Uri
    private val viewmodel : DiaryDetailViewModel by viewModels()
    private var currentDetailPosition: Int = 0

    // TODO positonだけうけとれたら、viewModel.diaryListではなく、detailはdetailで別にfirebaseから取得すればいいのかもしれない


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DiaryDetailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val monitor = binding.videoView

        binding.playBtn.setOnClickListener(){
            var videoPath: Uri? = null
            viewmodel.videoName.observe(viewLifecycleOwner){videoName -> String
                viewmodel.getVideoUri(videoName, {
                    videoPath = it
                    println("くんこ")

                })
            }
            println("うんこ")

            monitor.setVideoURI(videoPath)
            monitor.setOnPreparedListener {
                it.start()
            }
            monitor.setOnCompletionListener {
                it.release()
            }
        }


        return binding.root
    }


}