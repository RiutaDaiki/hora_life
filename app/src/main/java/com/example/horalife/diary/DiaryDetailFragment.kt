package com.example.horalife.diary

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.example.horalife.R
import com.example.horalife.databinding.DiaryDetailBinding

class DiaryDetailFragment(): Fragment() {
    private lateinit var binding: DiaryDetailBinding
    private lateinit var videoPath: Uri
    private val viewmodel : DiaryDetailViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DiaryDetailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val monitor = binding.videoView

//        viewmodel.videoName.observe(viewLifecycleOwner){
//            viewmodel.getVideoUri(it, {
//                videoPath = it
//            })
//        }

//        viewmodel.videoName.observe(viewLifecycleOwner, Observer {
//            viewmodel.getVideoUri(it, {
//                videoPath = it
//                println("ログ出ろ")
//            })
//        })

        binding.playBtn.setOnClickListener(){
            viewmodel.getVideoUri("", {
                videoPath = it
            })
            val path = Uri.parse("android.resource://" + this.requireContext().packageName + "/" + R.raw.kyoukai)
            monitor.setVideoPath(path.toString())
            monitor.start()
        }

        return binding.root
    }


}