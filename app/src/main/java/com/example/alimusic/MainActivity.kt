package com.example.alimusic

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alimusic.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider
import java.time.Duration
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mediaPlayer: MediaPlayer
    var isPlaying = false
    var isUserChanging = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareMusic()

        binding.btnPlayPause.setOnClickListener { configureMusic() }
        binding.btnGoBefore.setOnClickListener { goBeforeMusic() }
        binding.btnGoAfter.setOnClickListener { goAfterMusic() }
        binding.btnVolumeOnOff.setOnClickListener { configureVolune() }

        binding.sliderMain.addOnChangeListener { slider, value, fromUser ->

            binding.txtLeft.text = convertMillisToString(value.toLong())
            isUserChanging = fromUser

        }

        binding.sliderMain.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                mediaPlayer.seekTo(slider.value.toInt())
            }

        })

    }

    private fun configureVolune() {

    }

    private fun goAfterMusic() {

    }

    private fun goBeforeMusic() {

    }

    private fun configureMusic() {

        if (isPlaying) {
            mediaPlayer.pause()
            binding.btnPlayPause.setImageResource(R.drawable.ic_play)
            isPlaying = false
        } else {
            mediaPlayer.start()
            binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
            isPlaying = true
        }

    }

    private fun prepareMusic() {

        mediaPlayer = MediaPlayer.create(
            this,
            Uri.parse("https://ups.music-fa.com/tagdl/1402/DJ%20ESSI%20-%20Almas%20Remix%20Naser%20Zeynali%20(320).mp3?_gl=1*1u9ptpf*_ga*MzA4ODM4NTE3LjE2ODY5MTY5NzI.*_ga_FKQYXDVPQM*MTY5MzM4MjkxMC4xOC4wLjE2OTMzODI5MTAuMC4wLjA.&_ga=2.239055040.1590670383.1693375887-308838517.1686916972")
        )
        mediaPlayer.start()
        isPlaying = true
        binding.btnPlayPause.setImageResource(R.drawable.ic_pause)

        binding.sliderMain.valueTo = mediaPlayer.duration.toFloat()

        binding.txtRight.text = convertMillisToString(mediaPlayer.duration.toLong())

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (!isUserChanging) {
                        binding.sliderMain.value = mediaPlayer.currentPosition.toFloat()
                    }
                }
            }
        }, 1000, 1000)

    }

    private fun convertMillisToString(duration: Long): String {
        val second = duration / 1000 % 60
        val minute = duration / (1000 * 60) % 60

        return java.lang.String.format(Locale.US, "%02d:%02d", minute, second)
    }

}