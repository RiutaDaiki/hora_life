package com.example.horalife.diary_detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.horalife.R
import com.example.horalife.databinding.DiaryDetailBinding
import com.example.horalife.diary.DiaryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

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
        val twitterScope = CoroutineScope(Dispatchers.IO)
        binding.twitterBtn.setOnClickListener {
            twitterScope.launch {

                async(context = Dispatchers.IO) {
                    val cb = ConfigurationBuilder()
                    cb.setDebugEnabled(true)
                        .setOAuthConsumerKey("8ljdNrEJB0b374khutuGggzve")
                        .setOAuthConsumerSecret("yvnPkRYAxASWNJzw3GQGEC7ulDAFR7runNsS0iOPaunOHf7Z50")
                        .setOAuthAccessToken("1253319552970047489-cqYQ41Mf6tgk9x8ziTm5gAHXOBwglR")
                        .setOAuthAccessTokenSecret("U6qzoX9kFFV7q93F3n36ejWoBCmU3wTgmzyHEIvEfYbAk")                //各種キーの設定

                    val tf = TwitterFactory(cb.build())
                    val twitter = tf.getInstance()
                    twitter.updateStatus("Test2 tweet from Twitter4J  #twitter4j")    //ツイートの投稿
                }.await()

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