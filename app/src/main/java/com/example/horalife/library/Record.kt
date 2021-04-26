package com.example.horalife.library

import android.Manifest
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Looper.prepare
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil.setContentView
import com.example.horalife.MainActivity
import kotlinx.coroutines.NonCancellable.start
import java.io.IOException

class Record {
     var recorder: MediaRecorder? = null
     var player: MediaPlayer? = null
    private var fileName: String = ""
    private var playButton: PlayButton? = null

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("", "prepare() failed")
            }

            start()
        }
    }

    private fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else {
        stopRecording()
    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("", "prepare() failed")
            }
        }
    }

    private fun stopPlaying() {
        player?.release()
        player = null
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    internal inner class PlayButton(ctx: Context) : androidx.appcompat.widget.AppCompatButton(ctx) {
        var mStartPlaying = true
        var clicker: View.OnClickListener = View.OnClickListener {
            onPlay(mStartPlaying)
            text = when (mStartPlaying) {
                true -> "Stop playing"
                false -> "Start playing"
            }
            mStartPlaying = !mStartPlaying
        }

        init {
            text = "Start playing"
            setOnClickListener(clicker)
        }
    }

//    private fun sonota(){
//        var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
//
//        fileName = "${externalCacheDir.absolutePath}/audiorecordtest.3gp"
//
//        ActivityCompat.requestPermissions(MainActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION)
//
//        recordButton = RecordButton(this)
//        playButton = PlayButton(this)
//        val ll = LinearLayout(this).apply {
//            addView(recordButton,
//                    LinearLayout.LayoutParams(
//                            ViewGroup.LayoutParams.WRAP_CONTENT,
//                            ViewGroup.LayoutParams.WRAP_CONTENT,
//                            0f))
//            addView(playButton,
//                    LinearLayout.LayoutParams(
//                            ViewGroup.LayoutParams.WRAP_CONTENT,
//                            ViewGroup.LayoutParams.WRAP_CONTENT,
//                            0f))
//        }
//        setContentView(ll)
//    }


    }
