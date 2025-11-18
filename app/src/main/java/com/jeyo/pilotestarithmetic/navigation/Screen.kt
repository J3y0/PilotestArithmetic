package com.jeyo.pilotestarithmetic.navigation

sealed class Screen (val route: String) {
    object HomeScreen: Screen("home_screen")
    object TrainingScreen: Screen("training_screen") {
        fun RouteWithArg() = "training_screen/{questionCount}"

        fun CreateRoute(count: Int) = "training_screen/$count"
    }
    object ExamScreen: Screen("exam_screen")
    object ResultScreen: Screen("result_screen")
}