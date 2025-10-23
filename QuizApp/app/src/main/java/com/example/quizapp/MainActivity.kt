package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.ui.QuestionsActivity
import com.example.quizapp.utils.Constants

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val startButton : Button = findViewById(R.id.start_button)
        val inputName: EditText = findViewById(R.id.input_name)

        startButton.setOnClickListener {
            if(!inputName.text.isEmpty()){
                Intent(this@MainActivity, QuestionsActivity::class.java).also {
                    it.putExtra(Constants.USERNAME,inputName.text.toString())
                    startActivity(it)
                    finish()
                }
            }else{
                Toast.makeText(this@MainActivity,"Please enter your name", Toast.LENGTH_LONG).show()
            }
        }

    }
}