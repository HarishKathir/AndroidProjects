package com.example.quizapp.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizapp.R
import com.example.quizapp.model.Question
import com.example.quizapp.utils.Constants
import androidx.core.graphics.toColorInt


class QuestionsActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var name:String
    private var score = 0
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewProgress : TextView
    private lateinit var textViewQuestion : TextView
    private lateinit var  flagImage : ImageView
    private lateinit var  textViewOptionOne : TextView
    private lateinit var  textViewOptionTwo : TextView
    private lateinit var  textViewOptionThree : TextView
    private lateinit var  textViewOptionFour : TextView
    private lateinit var  checkbutton : Button

    private var currentPosition = 1
    private lateinit var questionsList: MutableList<Question>
    private var selectedAnswer = 0
    private lateinit var currentQuestion: Question
    private var answered: Boolean = false

    private var selectedOption = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_questions)

        if(intent.hasExtra(Constants.USERNAME)){
            name = intent.getStringExtra(Constants.USERNAME)!!
        }

        progressBar = findViewById(R.id.progressBar)
        textViewProgress = findViewById(R.id.text_view_progress_id)
        textViewQuestion = findViewById(R.id.question_text_view)
        flagImage = findViewById(R.id.image_flags)
        checkbutton = findViewById(R.id.button_check)

        textViewOptionOne = findViewById(R.id.option_one)
        textViewOptionTwo = findViewById(R.id.option_two)
        textViewOptionThree = findViewById(R.id.option_three)
        textViewOptionFour = findViewById(R.id.option_four)

        textViewOptionOne.setOnClickListener(this)
        textViewOptionTwo.setOnClickListener(this)
        textViewOptionThree.setOnClickListener(this)
        textViewOptionFour.setOnClickListener(this)
        checkbutton.setOnClickListener(this)

       questionsList = Constants.getQuestions()
        Log.d("Question Size", "${questionsList.size}")

        showNextQuestion()
    }

    private fun showNextQuestion(){

        if(currentPosition > questionsList.size){
            checkbutton.text = "FINISH"
                Intent(this@QuestionsActivity, ResultActivity::class.java).also {
                    it.putExtra(Constants.USERNAME,name)
                    it.putExtra(Constants.SCORE,score)
                    it.putExtra(Constants.TOTAL_QUESTIONS,questionsList.size)
                    startActivity(it)
                }
        }else{
            checkbutton.text = "CHECK"

            resetOptionBackground()

            currentQuestion = questionsList[currentPosition-1]
            flagImage.setImageResource(currentQuestion.image)
            progressBar.progress = currentPosition
            textViewProgress.text = "$currentPosition/${progressBar.max}"
            textViewQuestion.text = currentQuestion.question
            textViewOptionOne.text = currentQuestion.optionOne
            textViewOptionTwo.text = currentQuestion.optionTwo
            textViewOptionThree.text = currentQuestion.optionThree
            textViewOptionFour.text = currentQuestion.optionFour
        }

        currentPosition++
        answered = false

    }

    private fun resetOptionBackground(){
        val options = mutableListOf<TextView>()
        options.add(textViewOptionOne)
        options.add(textViewOptionTwo)
        options.add(textViewOptionThree)
        options.add(textViewOptionFour)

        for (option in options){
            option.setTextColor(Color.BLACK)
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
        }

    }

    private fun selectedOption(textView: TextView,selectedOptionNumber:Int){
        resetOptionBackground()

        selectedOption = selectedOptionNumber

        textView.setTextColor("#3F51B5".toColorInt())
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(this,R.drawable.selected_option_border_bg)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.option_one -> {
                selectedOption(textViewOptionOne,1)
            }
            R.id.option_two -> {
                selectedOption(textViewOptionTwo,2)
            }
            R.id.option_three -> {
                selectedOption(textViewOptionThree,3)
            }
            R.id.option_four -> {
                selectedOption(textViewOptionFour,4)
            }
            R.id.button_check -> {
                if(!answered){
                    if (selectedOption == 0) {
                        // no option selected — show a message
                        Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                    } else {
                        checkAnswer()
                    }                }else{
                    showNextQuestion()
                }
                selectedAnswer = 0
                selectedOption = 0
            }
        }
    }

    private fun checkAnswer() {
        answered = true

        if(selectedOption == currentQuestion.correctAnswer){
            highlightAnswer(selectedOption)
            score++
        }else{
            when(selectedOption){
                1 ->{ textViewOptionOne.background = ContextCompat.getDrawable(this,R.drawable.false_option_border_bg) }
                2 ->{ textViewOptionTwo.background = ContextCompat.getDrawable(this,R.drawable.false_option_border_bg) }
                3 ->{ textViewOptionThree.background = ContextCompat.getDrawable(this,R.drawable.false_option_border_bg) }
                4 ->{ textViewOptionFour.background = ContextCompat.getDrawable(this,R.drawable.false_option_border_bg) }
            }
        }
        checkbutton.text = "NEXT"
        showSolution()
    }

    private fun showSolution() {
        selectedAnswer = currentQuestion.correctAnswer

        highlightAnswer(selectedAnswer)
    }

    private fun highlightAnswer(answer:Int){
        when(answer){
            1 ->{ textViewOptionOne.background = ContextCompat.getDrawable(this,R.drawable.true_option_border_bg) }
            2 ->{ textViewOptionTwo.background = ContextCompat.getDrawable(this,R.drawable.true_option_border_bg) }
            3 ->{ textViewOptionThree.background = ContextCompat.getDrawable(this,R.drawable.true_option_border_bg) }
            4 ->{ textViewOptionFour.background = ContextCompat.getDrawable(this,R.drawable.true_option_border_bg) }
        }
    }
}