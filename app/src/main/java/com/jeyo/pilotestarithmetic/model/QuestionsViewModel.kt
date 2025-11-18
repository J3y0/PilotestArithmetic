package com.jeyo.pilotestarithmetic.model

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jeyo.pilotestarithmetic.Equation
import com.jeyo.pilotestarithmetic.SessionTimer

class QuestionsViewModel : ViewModel() {
    private var mode = ""
    var equationLength = mutableIntStateOf(8)
        private set
    var score = mutableIntStateOf(0)
        private set
    var currentQuestion = mutableIntStateOf(1)
        private set
    var totalQuestions = mutableIntStateOf(1)
        private set
    var equations = mutableStateListOf<Equation>()
        private set
    var userAnswers = mutableStateListOf<Int>()
        private set
    var currentEquation = mutableStateOf(Equation(equationLength.intValue))
        private set

    val timer = SessionTimer()

    fun getMode(): String {
        return mode
    }

    fun setMode(newMode: String) {
        mode = newMode
    }

    fun isLastQuestion(): Boolean {
        return currentQuestion.intValue == totalQuestions.intValue
    }

    fun setNewEquation(length: Int) {
        currentEquation.value = Equation(length)
    }

    fun incrementCurrentQuestion() {
        currentQuestion.intValue++
    }

    fun incrementScore() {
        score.intValue++
    }

    fun setTotalQuestions(question: Int) {
        totalQuestions.intValue = question
    }

    fun saveEquation() {
        equations.add(currentEquation.value)
    }

    fun saveUserAnswer(answer: Int) {
        userAnswers.add(answer)
    }
}