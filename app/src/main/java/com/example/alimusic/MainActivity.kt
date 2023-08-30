package com.example.alimusic

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alimusic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var mediaPlayer: MediaPlayer
    var isPlaying = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareMusic()

        binding.btnPlayPause.setOnClickListener { configureMusic() }
        binding.btnGoBefore.setOnClickListener { goBeforeMusic() }
        binding.btnGoAfter.setOnClickListener { goAfterMusic() }
        binding.btnVolumeOnOff.setOnClickListener { configureVolune() }

    }

    private fun configureVolune() {

    }

    private fun goAfterMusic() {

    }

    private fun goBeforeMusic() {

    }

    private fun configureMusic() {

        if (isPlaying){
            mediaPlayer.pause()
            binding.btnPlayPause.setImageResource(R.drawable.ic_play)
            isPlaying=false
        }else{
            mediaPlayer.start()
            binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
            isPlaying=true
        }

    }

    private fun prepareMusic() {

        mediaPlayer = MediaPlayer.create(this, Uri.parse("https://ups.music-fa.com/tagdl/1402/DJ%20ESSI%20-%20Almas%20Remix%20Naser%20Zeynali%20(320).mp3?_gl=1*1u9ptpf*_ga*MzA4ODM4NTE3LjE2ODY5MTY5NzI.*_ga_FKQYXDVPQM*MTY5MzM4MjkxMC4xOC4wLjE2OTMzODI5MTAuMC4wLjA.&_ga=2.239055040.1590670383.1693375887-308838517.1686916972"))
        mediaPlayer.start()
        isPlaying = true
        binding.btnPlayPause.setImageResource(R.drawable.ic_pause)

    }
}