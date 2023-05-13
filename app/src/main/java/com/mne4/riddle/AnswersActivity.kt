package com.mne4.riddle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.view.get
import com.mne4.riddle.databinding.ActivityAnswersBinding

class AnswersActivity : AppCompatActivity() {
    lateinit var binding: ActivityAnswersBinding
    var answers = mutableListOf<String>()
    lateinit var answer: String
    lateinit var trueAnswer: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnswersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Выберите ответ"
        init()
    }

    private fun init() {
        for (i in (0 until 10)) {
            answers.add(intent.getStringExtra("Answer $i").toString())
        }
//        trueAnswer = intent.getStringExtra("TrueAnswer").toString()

        for (i in (0 until 10)) {
            var button = RadioButton(this)
            button.text = "${answers[i]}"
            binding.RadioGroup.addView(button)
        }
        binding.RadioGroup.setOnCheckedChangeListener { group, id ->
            findViewById<RadioButton>(id)?.apply {
                binding.button.isEnabled = true
                answer = text.toString()
            }
        }
    }

    fun onAnswerClick(view: View) {
        intent.putExtra("answer", answer)
        setResult(RESULT_OK, intent)
        finish()
    }
}