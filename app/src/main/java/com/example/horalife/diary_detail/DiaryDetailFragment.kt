package com.example.horalife.diary_detail

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.horalife.R
import com.example.horalife.databinding.DiaryDetailBinding

class DiaryDetailFragment(): Fragment() {
    private val args: DiaryDetailFragmentArgs by navArgs()
    private lateinit var binding: DiaryDetailBinding
    private val viewmodel : DiaryDetailViewModel by viewModels()
    private lateinit var rowVideoUri: Uri

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.diary_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId){
            R.id.delete ->{
                val dialog = ConfirmDeleteDialog()
                dialog.show(parentFragmentManager, null)
            }
        }
        return true
    }

        override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DiaryDetailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val monitor = binding.videoView
        binding.videoProgressBar.visibility = android.widget.ProgressBar.INVISIBLE
            setHasOptionsMenu(true)

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