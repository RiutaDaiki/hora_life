package com.example.horalife.example

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.horalife.R
import com.example.horalife.Sound

class ExampleFragmentViewModel(application: Application): AndroidViewModel(application) {
    lateinit var soundPool: SoundPool
    var soundId: Int = 0
    private val context = getApplication<Application>().applicationContext
    val dataList = listOf<Sound>(Sound(R.string.sirabe, R.raw.no), Sound(R.string.otsu, R.raw.one), Sound(R.string.kan, R.raw.one), Sound(R.string.yuri, R.raw.one), Sound(R.string.tome, R.raw.one))

    fun outPutSound(soundFile: Int) {

        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder().setAudioAttributes(AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA).build()).setMaxStreams(1).build()
        } else {
            SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }

        soundId = soundPool.load(context, soundFile, 1)
        soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
        }
        }
    }
