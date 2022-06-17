package com.example.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_text1,true),
        Question(R.string.question_text2,true),
        Question(R.string.question_text3,false),
        Question(R.string.question_text4,true),
        Question(R.string.question_text5,false),
        Question(R.string.question_text6,true))

    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        trueButton.setOnClickListener { view: View ->
            Toast.makeText(
                this,
                R.string.correct_toast,
                Toast.LENGTH_SHORT
            )
                .show()
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view: View ->
            Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT
            )
                .show()
            checkAnswer(false)
        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            val questionTextResId = questionBank[currentIndex].textResId
            questionTextView.setText(questionTextResId)
            updateQuestion()
        }
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText((questionTextResId))
        updateQuestion()}
    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = questionBank[ currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast}
        else
        {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId,Toast.LENGTH_SHORT).show()
    }
}