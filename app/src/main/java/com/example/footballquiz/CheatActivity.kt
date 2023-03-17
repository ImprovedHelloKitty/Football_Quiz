package com.example.footballquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.footballquiz.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_SHOWN = "com.example.FootballQuiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.example.FootballQuiz.answer_is_true"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCheatBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.showAnswerButton.setOnClickListener{
            //Once they click the show answer button enable cheating
            val answerText = when {
                answerIsTrue -> R.string.correct_string
                else -> R.string.incorrect_string
            }
            quizViewModel.hasCheated = true
            setAnswerShownResult(quizViewModel.hasCheated)
        }
        if(quizViewModel.hasCheated) {
            setAnswerShownResult(quizViewModel.hasCheated)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val answerText = when {
            answerIsTrue -> R.string.correct_string
            else -> R.string.incorrect_string
        }
        binding.answerTextView.setText(answerText)
        val data = Intent().apply{
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply{
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}