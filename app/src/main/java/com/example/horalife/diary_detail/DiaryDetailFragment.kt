package com.example.horalife.diary_detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.horalife.R
import com.example.horalife.databinding.DiaryDetailBinding
import com.example.horalife.diary.DiaryViewModel

class DiaryDetailFragment() : Fragment() {
    private lateinit var binding: DiaryDetailBinding
    private val viewModel: DiaryViewModel by activityViewModels()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.diary_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            R.id.delete -> {
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
        binding.viewmodel = viewModel

        val monitor = binding.videoView
        binding.videoProgressBar.visibility = android.widget.ProgressBar.INVISIBLE
        setHasOptionsMenu(true)
        if (viewModel.selectedPosition.value != null) {
            viewModel.getBitMap(viewModel.selectedPosition.value!!) {
                binding.thumView.setImageBitmap(it)
            }
        }

        binding.playBtn.setOnClickListener() {
            binding.videoProgressBar.visibility = android.widget.ProgressBar.VISIBLE
            viewModel.getVideoUri({
                monitor.setVideoURI(it)
                monitor.setOnPreparedListener {
                    binding.videoProgressBar.visibility = android.widget.ProgressBar.INVISIBLE
                    binding.thumView.visibility = android.widget.ImageView.INVISIBLE
                    monitor.start()
                }
            }) {
                Toast.makeText(context, "読み込みに失敗しました", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }

}