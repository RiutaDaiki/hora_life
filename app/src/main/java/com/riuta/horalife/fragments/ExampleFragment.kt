package com.riuta.horalife.fragments

import android.content.Context
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.riuta.horalife.R
import com.riuta.horalife.dataClass.Sound
import com.riuta.horalife.databinding.ExampleFragmentBinding
import com.riuta.horalife.recycler.RecyclerViewAdapter
import com.riuta.horalife.viewModel.YouViewModel

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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ExampleFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = RecyclerViewAdapter(dataList, viewLifecycleOwner, context)
        binding.recyclerView.adapter = adapter

        setTheme()

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

    private fun setTheme(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val isDarkTHeme = sharedPref.getBoolean(getString(R.string.current_display_theme), false)
        when(isDarkTHeme){
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
