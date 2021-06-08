package com.example.horalife.diary_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.horalife.BuildConfig
import com.example.horalife.R
import com.example.horalife.databinding.DiaryDetailBinding
import com.example.horalife.diary.DiaryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.io.File
import java.sql.Timestamp
import kotlin.coroutines.CoroutineContext


class DiaryDetailFragment() : Fragment(), CoroutineScope {

    private val TWITTER_CODE = 140
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
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

        binding.twitterBtn.setOnClickListener {
            twitter()
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

    fun twitter(){
        val tweetText = viewModel.selectedDiary.value?.comment ?: ""

        val intent = Intent(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, "$tweetText\n#HORALIFE")
                    .putExtra(Intent.EXTRA_STREAM, createOutPutFile())
                    .setType("video/*")
                    .setPackage("com.twitter.android")
        startActivityForResult(intent, TWITTER_CODE)
    }

    fun createOutPutFile(): Uri {
        val selectedVideo = viewModel.selectedDiary.value?.videoPath
        val file = File(selectedVideo)

        val uri = FileProvider.getUriForFile(
                this.requireContext(),
                this.requireContext().packageName + ".provider",
                file
        )
        return uri
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
//            TWITTER_CODE -> ここでツイート完了したか判定してトースト出したい
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}




