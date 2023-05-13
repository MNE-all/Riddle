package com.mne4.riddle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.mne4.riddle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var gameCount = 0
    var trueCount = 0
    private var launcher: ActivityResultLauncher<Intent>? = null
    private var queueRiddles = mutableMapOf<String, String>()
    private val riddles = mutableMapOf(Pair("Страну чудес откроем мы\n" +
            "И встретимся с героями\n" +
            "В строчках,\n" +
            "На листочках,\n" +
            "Где станции на точках.", "Книга"),
    Pair("Пустые отдыхаем,\n" +
            "А полные шагаем.", "Сапоги"),
    Pair("Деревянный брусок,\n" +
            "А на нем растет лесок.", "Щётка"),
    Pair("Ёжик странный у Егорки\n" +
            "На окне сидит в ведерке.\n" +
            "День и ночь он дремлет,\n" +
            "Спрятав ножки в землю.", "Кактус"),
    Pair("На раскрашенных страницах\n" +
            "Много праздников хранится.", "Календарь"),
    Pair("Маленькие домики по улице бегут,\n" +
            "Мальчиков и девочек домики везут.", "Машины"),
    Pair("В Полотняной стране\n" +
            "По реке Простыне\n" +
            "Плывет пароход\n" +
            "То назад, то вперед,\n" +
            "А за ним такая гладь —\n" +
            "Ни морщинки не видать.", "Утюг"),
    Pair("Музыкант, певец, рассказчик —\n" +
            "А всего труба да ящик.", "Граммофон"),
    Pair("Вот иголки и булавки\n" +
            "Выползают из-под лавки,\n" +
            "На меня они глядят,\n" +
            "Молока они хотят.", "Ёж"),
    Pair("В брюшке — баня,\n" +
            "В носу — решето,\n" +
            "Нос — хоботок,\n" +
            "На голове — пупок,\n" +
            "Всего одна рука\n" +
            "Без пальчиков,\n" +
            "И та — на спине\n" +
            "Калачиком.", "Чайник"),
    Pair("Ах, не трогайте меня:\n" +
            "Обожгу и без огня!", "Крапива"),
    Pair("Чем больше отдаю,\n" +
            "Тем больше вырастаю,\n" +
            "Величину свою\n" +
            "Отдачей измеряю.", "Яма"),
    Pair("Если день нахмурится,\n" +
            "Если дождь пойдет —\n" +
            "Выйдет он на улицу,\n" +
            "Надо мной вспорхнет.", "Зонт"),
    Pair("Речка спятила с ума —\n" +
            "По домам пошла сама.", "Водопровод"),
    Pair("Стоит дуб,\n" +
            "В нем двенадцать гнезд,\n" +
            "В каждом гнезде\n" +
            "По четыре яйца,\n" +
            "В каждом яйце\n" +
            "По семи цыпленков.", "Год")
    )
    lateinit var riddle: Pair<String, String>
    private lateinit var binding: ActivityMainBinding
    private lateinit var answers: MutableList<String>
    private var userAnswer: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Отгадай загадку"
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                binding.buttonAnswer.isEnabled = false
                if (gameCount < 10) {
                    binding.buttonRiddle.isEnabled = true
                }
                userAnswer = it.data?.getStringExtra("answer")
                result()
            }
        }
        onGameStart()
    }

    fun onGameStart() {
        for (i in riddles) {
            queueRiddles[i.key] = i.value
        }
        for (i in 0 until 5) {
            val temp = queueRiddles.toList().random()
            queueRiddles.remove(temp.first)
        }
    }

    private fun result() {
        binding.textViewUserAnswer.text = userAnswer
        if (userAnswer == riddle.second) {
            trueCount += 1
            binding.textViewUserAnswer.setBackgroundColor(getColor(R.color.green))
        }
        else {
            binding.textViewUserAnswer.setBackgroundColor(getColor(R.color.red))
        }

    }

    fun onRiddleClick (view: View) {
        binding.textViewUserAnswer.text = ""
        binding.textViewUserAnswer.setBackgroundColor(getColor(R.color.white))
        gameCount += 1
        binding.textViewAnswerCount.text = "$gameCount/10"
        var queue = mutableListOf<Pair<String, String>>()
        for (tempRiddles in riddles) {
            queue.add(tempRiddles.toPair())
        }
        riddle = queueRiddles.toList().random()
        queueRiddles.remove(riddle.first)
        queue.remove(riddle)

        answers = mutableListOf(riddle.second)
        for (i in 0 until 9) {
            val answer = queue.random()
            answers.add(answer.second)
            queue.remove(answer)

        }

        randomize()

        binding.textViewRiddle.text = riddle.first
        binding.buttonAnswer.isEnabled = true
        binding.buttonRiddle.isEnabled = false
    }

    private fun randomize() {
        var tempAnswers = mutableListOf<String>()
        for (answer in answers) {
            tempAnswers.add(answer)
        }

        answers = mutableListOf()
        for (i in (0 until 10)) {
            val answer = tempAnswers.random()
            answers.add(answer)
            tempAnswers.remove(answer)
        }

    }

    fun onStatisticClick(view: View) {
        var intent = Intent(this, StatisticActivity::class.java)
        intent.putExtra("winCount", trueCount)
        intent.putExtra("gameCount", gameCount)
        startActivity(intent)
    }

    fun onAnswerClick(view: View) {
        var intent = Intent(this, AnswersActivity::class.java)
//        intent.putExtra("TrueAnswer", riddle.second)
        for (i in answers.indices) {
            intent.putExtra("Answer $i", answers[i])
        }

        launcher?.launch(intent)
    }
}