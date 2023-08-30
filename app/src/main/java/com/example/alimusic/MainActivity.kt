package com.example.alimusic

import android.media.AudioManager
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
    lateinit var timer: Timer
    var isPlaying = false
    var isUserChanging = false
    var isMuted = false

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

        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        if (isMuted){
            audioManager.adjustVolume(AudioManager.ADJUST_UNMUTE,AudioManager.FLAG_SHOW_UI)
            binding.btnVolumeOnOff.setImageResource(R.drawable.ic_volume_on)
            isMuted = false
        }else{
            audioManager.adjustVolume(AudioManager.ADJUST_MUTE,AudioManager.FLAG_SHOW_UI)
            binding.btnVolumeOnOff.setImageResource(R.drawable.ic_volume_off)
            isMuted = true
        }

    }

    private fun goAfterMusic() {

        val now = mediaPlayer.currentPosition
        val newValue = now + 10000
        mediaPlayer.seekTo(newValue)

    }

    private fun goBeforeMusic() {

        val now = mediaPlayer.currentPosition
        val newValue = now - 10000
        mediaPlayer.seekTo(newValue)

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

        timer = Timer()
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

    override fun onDestroy() {
        super.onDestroy()

//        mediaPlayer.isLooping = true
        timer.cancel()
        mediaPlayer.release()

    }

}