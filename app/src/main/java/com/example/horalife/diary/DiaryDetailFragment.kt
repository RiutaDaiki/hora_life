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
import com.example.horalife.databinding.DiaryDetailBinding
import com.example.horalife.databinding.DiaryFragmentBinding

class DiaryDetailFragment(): Fragment() {
    private lateinit var binding: DiaryDetailBinding
    private lateinit var videoPath: Uri

    // TODO positonだけうけとれたら、viewModel.diaryListではなく、detailはdetailで別にfirebaseから取得すればいいのかもしれない
    fun prepareVideo(position: Int){
//        videoPath = path.toUri()
        Log.d("ffffffffffffffffffffff", position.toString())
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DiaryDetailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val monitor = binding.videoView

        binding.playBtn.setOnClickListener(){
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