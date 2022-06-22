package com.example.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextImageButton: ImageButton
    private lateinit var prevImageButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_text1,true),
        Question(R.string.question_text2,true),
        Question(R.string.question_text3,false),
        Question(R.string.question_text4,true),
        Question(R.string.question_text5,false),
        Question(R.string.question_text6,true))

    private var currentIndex = 0
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider.of(this).get(QuizViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        val currentIndex =
            savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        val provider: ViewModelProvider = ViewModelProvider.of(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel:$quizViewModel")
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextImageButton = findViewById(R.id.next_button)
        prevImageButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)
        trueButton.setOnClickListener { view: View ->
            Toast.makeText(
                this,
                R.string.correct_toast,
                Toast.LENGTH_SHORT
            )
                .show()
            checkAnswer(true)
            view.isEnabled=false
            falseButton.isEnabled=false
        }
        falseButton.setOnClickListener { view: View ->
            Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT
            )
                .show()
            checkAnswer(false)
            view.isEnabled=false
            trueButton.isEnabled=false
        }
        nextImageButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            quizViewModel.moveToNext()
            updateQuestion()
            val questionTextResId = questionBank[currentIndex].textResId
            questionTextView.setText(questionTextResId)
            updateQuestion()
            falseButton.isEnabled=true
            trueButton.isEnabled=true
        }
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText((questionTextResId))
        updateQuestion()
        prevImageButton.setOnClickListener {
            if (currentIndex == 0)currentIndex =+ questionBank.size
            currentIndex = (currentIndex - 1) %questionBank.size
            updateQuestion()
            falseButton.isEnabled=true
            trueButton.isEnabled=true
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX,quizViewModel.currentIndex)
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }


    private fun updateQuestion(){
        /*val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)*/
        val questionTextResId = quizViewModel.currentQuestionText
    }
    private fun checkAnswer(userAnswer:Boolean){
        /*val correctAnswer = questionBank[ currentIndex].answer*/
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast}
        else
        {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId,Toast.LENGTH_SHORT).show()
    }
}