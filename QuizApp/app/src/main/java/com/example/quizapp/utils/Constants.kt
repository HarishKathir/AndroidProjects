package com.example.quizapp.utils

import com.example.quizapp.model.Question
import com.example.quizapp.R

object Constants{

    const val USERNAME = "username"
    const val TOTAL_QUESTIONS = "total_questions"
    const val SCORE = "correct_answers"

    fun getQuestions(): MutableList<Question>{
        val questions = mutableListOf<Question>()
        val question1 = Question(
            id = 1,
            "What Country does this flag belongs to?",
            R.drawable.italyflag,
            "Italy",
            "India",
            "USA",
            "Brzail",
            1
        )
        questions.add(question1)
        val question2 = Question(
            id = 2,
             "What Country does this flag belong to?",
              R.drawable.brazilflag,
              "Germany",
              "Brazil",
             "India",
            "Romania",
             2
        )
        questions.add(question2)

        val question3 = Question(
            id = 3,
             "What Country does this flag belong to?",
             R.drawable.finlandflag,
             "Finland",
             "France",
             "Spain",
            "Nigeria",
             1
        )
        questions.add(question3)

        val question4 = Question(
            id = 4,
             "What Country does this flag belong to?",
             R.drawable.franceflag,
             "Argentina",
             "France",
             "Germany",
            "India",
             2
        )
        questions.add(question4)

        val question5 = Question(
            id = 5,
             "What Country does this flag belong to?",
             R.drawable.germanyflag,
             "Spain",
             "Nigeria",
             "Germany",
            "Finland",
             3
        )
        questions.add(question5)

        val question6 = Question(
            id = 6,
             "What Country does this flag belong to?",
             R.drawable.indiaflag,
             "India",
             "Romania",
             "Brazil",
            "Argentina",
             1
        )
        questions.add(question6)

        val question7 = Question(
            id = 7,
             "What Country does this flag belong to?",
             R.drawable.nigeriaflag,
             "Nigeria",
             "Finland",
             "Spain",
            "France",
             1
        )
        questions.add(question7)

        val question8 = Question(
            id = 8,
             "What Country does this flag belong to?",
             R.drawable.romaniaflag,
             "Germany",
             "Romania",
             "India",
            "Argentina",
             2
        )
        questions.add(question8)

        val question9 = Question(
            id = 9,
             "What Country does this flag belong to?",
             R.drawable.spainflag,
             "Spain",
             "Brazil",
             "France",
            "Finland",
             1
        )
        questions.add(question9)

        val question10 = Question(
            id = 10,
             "What Country does this flag belong to?",
             R.drawable.argentinaflag,
             "Argentina",
             "India",
             "USA",
            "Brazil",
             1
        )
        questions.add(question10)
        return questions
    }
}