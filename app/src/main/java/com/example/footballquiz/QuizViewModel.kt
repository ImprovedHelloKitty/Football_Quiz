package com.example.footballquiz

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import kotlin.random.Random

private const val TAG = "QuizViewModel"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    //This array is used to keep track of of which questions were answered
    val isAnswered = arrayOf(false, false, false, false, false, false, false, false, false, false)

    var hasCheated = false

    private val questionBank = listOf(
        Questions(R.string.question1, true),
        Questions(R.string.question2, false),
        Questions(R.string.question3, false),
        Questions(R.string.question4, false),
        Questions(R.string.question5, true),
        Questions(R.string.question6, false),
        Questions(R.string.question7, true),
        Questions(R.string.question8, false),
        Questions(R.string.question9, true),
        Questions(R.string.question10, true)
    )

    val cheatQuestions = arrayOf(false, false, false, false, false, false, false, false, false, false)

    var isCheater:Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?:false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    var isAnswerRevealed = false

    private var currentIndex =0

    private var numberCorrect = 0

    val currentQuestionAnswer: Boolean
    get() = questionBank[currentIndex].answer

    val currentQuestionText:Int
    get() = questionBank[currentIndex].textResId

    val questionBankSize: Int
    get() = questionBank.size

    val getCurrentIndex: Int
    get() = currentIndex

    val getNumberCorrect: Int
    get() = numberCorrect

    fun moveToNext(){
        Log.d(TAG, "Updating question text", Exception())
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev(){
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    fun increaseCorrect(){
        numberCorrect++
    }

    fun randomQuestion(){
        currentIndex = Random.nextInt(0,questionBank.size-1)
    }

}