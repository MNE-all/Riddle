package com.mne4.riddle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatViewInflater
import com.mne4.riddle.databinding.ActivityStatisticBinding

class StatisticActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.statistics)
        val win = intent.getIntExtra("winCount", 0)
        val game = intent.getIntExtra("gameCount", 0)
        binding.textViewWin.text = win.toString()
        binding.textViewPercent.text = "${100 * win / game}%"
    }

    fun onBackClick(view: View) {
        finish()
    }
}