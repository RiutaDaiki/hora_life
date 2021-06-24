package com.example.horalife.fragments

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.horalife.R
import com.example.horalife.entity.Sound
import com.example.horalife.databinding.ExampleFragmentBinding
import com.example.horalife.recycler.RecyclerViewAdapter

class ExampleFragment : Fragment() {
    private lateinit var adapter: RecyclerViewAdapter
    val dataList = listOf<Sound>(Sound(R.string.sirabe, R.raw.sirabe),
            Sound(R.string.otsu, R.raw.otsu), Sound(R.string.kan, R.raw.kan),
            Sound(R.string.yuri, R.raw.yuri), Sound(R.string.tome, R.raw.tome),
            Sound(R.string.ots_atari, R.raw.otsu_atari),
            Sound(R.string.ots_ots_atari, R.raw.ots_ots_atari),
            Sound(R.string.ots_yurisori, R.raw.ots_yurisori),
            Sound(R.string.kan_hira_yuri, R.raw.kan_hira_yuri),
            Sound(R.string.betsu_yuri, R.raw.betsu_yuri))
    lateinit var player: MediaPlayer
//<<<<<<< HEAD
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val binding = ExampleFragmentBinding.inflate(inflater, container, false)
//=======
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ExampleFragmentBinding.inflate(layoutInflater, container, false)

//>>>>>>> parent of b293fc4 (コードを整えた)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = RecyclerViewAdapter(dataList, viewLifecycleOwner, context)
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    fun playMedia(file: Int, ct: Context) {
        player = MediaPlayer.create(ct, file)
        player.isLooping = false
        player.start()
        player.setOnCompletionListener { mp -> player.stop() }
    }

}
