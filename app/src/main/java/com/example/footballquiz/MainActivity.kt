package com.example.footballquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.activity.viewModels
import com.example.footballquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random


//private lateinit var true_button:Button
//private lateinit var false_button:Button
//Create and array of answers

private const val TAG = "MainActivity";



//This array is used to keep track of of which questions were answered

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
   private val quizViewModel: QuizViewModel by viewModels()

   private val cheatLauncher = registerForActivityResult(
       ActivityResultContracts.StartActivityForResult()
   ){result ->
       // Handle the Results
       if (result.resultCode == Activity.RESULT_OK){
           quizViewModel.isCheater =
               result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
       }
       if (quizViewModel.isCheater){
           quizViewModel.cheatQuestions[quizViewModel.getCurrentIndex] = true
       }
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")
        //Hook up the button to its id
        //true_button = findViewById(R.id.true_button)
        //false_button = findViewById(R.id.false_button)

        //what happens if you click on the buttons
        binding.trueButton.setOnClickListener {view: View ->
            //Do something if you click on true button
            //have a correct toast that pops up
           checkAnswer(true,view)
            binding.trueButton.isEnabled = !(binding.trueButton.isEnabled)
            binding.falseButton.isEnabled = !(binding.falseButton.isEnabled)
            quizViewModel.isAnswered[quizViewModel.getCurrentIndex] = true
            isQuizFinished()
        }

        binding.falseButton.setOnClickListener {view: View ->
            //Do something if you click on false button
            checkAnswer(false,view)
            binding.trueButton.isEnabled = !(binding.trueButton.isEnabled)
            binding.falseButton.isEnabled = !(binding.falseButton.isEnabled)
            quizViewModel.isAnswered[quizViewModel.getCurrentIndex] = true
            isQuizFinished()
        }
        //If you click on the text view then you switch to the next question
        binding.questionTextView.setOnClickListener{view: View ->
            quizViewModel.moveToNext()
            updateQuestion()
        }

        //onset listener for the next button
        //ie what happen if you press th next button

        binding.nextButton.setOnClickListener{view: View ->
            //This will check whether or not the previous question has been answered and will
            //either turn the buttons on or off
            quizViewModel.moveToNext()
            updateQuestion()
        }

        //Here is where I added in the binding for the prev button
        binding.prevButton.setOnClickListener { view: View ->
            if (quizViewModel.getCurrentIndex > 0) {
                //This will check whether or not the previous question has been answered and will
                //either turn the buttons on or off
                quizViewModel.moveToPrev()
                updateQuestion()
            }
        }

        binding.randomButton.setOnClickListener {
            quizViewModel.randomQuestion()
            updateQuestion()
        }

        binding.cheatButton.setOnClickListener{
            //Start cheat activity
            //val intent = Intent(this, CheatActivity::class.java)
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            //startActivity(intent)
            cheatLauncher.launch(intent)
        }

        //This will get you the id for the current question in the question bank
        updateQuestion()


    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG, "onStart() is called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() is called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() is called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() is called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() is called")
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
        if (quizViewModel.isAnswered[((quizViewModel.getCurrentIndex) % quizViewModel.questionBankSize)]) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
    }

    private fun checkAnswer(userAnswer:Boolean, v:View){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.cheatQuestions[quizViewModel.getCurrentIndex] -> R.string.judgement_toast
            userAnswer == correctAnswer -> R.string.correct_string
            else-> R.string.incorrect_string
        }
        if(userAnswer == correctAnswer){
            quizViewModel.increaseCorrect()
        }
        //Toast.makeText( this, messageResId, Toast.LENGTH_SHORT).show()
        Snackbar.make( v, messageResId, Snackbar.LENGTH_SHORT).show()
    }

    private fun isQuizFinished(){
        var answers = 0
        for(i in 0 .. (quizViewModel.questionBankSize -1)){
            if(quizViewModel.isAnswered[i] == true){
                answers++
            }
        }

        if(answers == quizViewModel.questionBankSize){
            var percent = ((quizViewModel.getNumberCorrect.toDouble() / quizViewModel.questionBankSize) * 100)
            val printOut = "You got " + percent + "% correct!"
            Toast.makeText(this, printOut, Toast.LENGTH_SHORT).show()
        }
    }
}


