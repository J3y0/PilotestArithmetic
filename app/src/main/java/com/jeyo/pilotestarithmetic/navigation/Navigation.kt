package com.jeyo.pilotestarithmetic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.jeyo.pilotestarithmetic.model.QuestionsViewModel
import com.jeyo.pilotestarithmetic.ui.screens.ExamScreen
import com.jeyo.pilotestarithmetic.ui.screens.HomeScreen
import com.jeyo.pilotestarithmetic.ui.screens.ResultScreen
import com.jeyo.pilotestarithmetic.ui.screens.TrainingScreen

const val EXAM_NAVGRAPH_ROUTE = "exam_graph"
const val TRAINING_NAVGRAPH_ROUTE = "training_graph"

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                onNavigateTrainingScreen = { count ->
                    navController.navigate(Screen.TrainingScreen.CreateRoute(count))
                },
                onNavigateExamScreen = {
                    navController.navigate(EXAM_NAVGRAPH_ROUTE)
                }
            )
        }

        navigation(
            startDestination = Screen.TrainingScreen.route,
            route = TRAINING_NAVGRAPH_ROUTE
        ) {
            composable(
                route = Screen.TrainingScreen.RouteWithArg(),
                arguments = listOf(navArgument("questionCount") {
                    defaultValue = 20
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val questionCount = backStackEntry.arguments?.getInt("questionCount") ?: 20

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(TRAINING_NAVGRAPH_ROUTE)
                }
                val questionsViewModel = viewModel<QuestionsViewModel>(parentEntry)

                LaunchedEffect(Unit) {
                    questionsViewModel.setTotalQuestions(questionCount)
                    questionsViewModel.timer.startSilentTimer()
                }

                TrainingScreen(
                    questionsViewModel = questionsViewModel,
                    onNavigateResultScreen = {
                        questionsViewModel.timer.stopSilentTimer()
                        navController.navigate(
                            route = Screen.ResultScreen.route
                        )
                    }
                )
            }

            composable(route = Screen.ResultScreen.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(TRAINING_NAVGRAPH_ROUTE)
                }
                val questionsViewModel = viewModel<QuestionsViewModel>(parentEntry)

                ResultScreen(
                    questionsViewModel = questionsViewModel,
                    onNavigateHomeScreen = {
                        questionsViewModel.timer.resetTimer()
                        navController.navigate(route = Screen.HomeScreen.route)
                    }
                )
            }
        }

        navigation(startDestination = Screen.ExamScreen.route, route = EXAM_NAVGRAPH_ROUTE) {
            composable(route = Screen.ExamScreen.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(EXAM_NAVGRAPH_ROUTE)
                }
                val questionsViewModel = viewModel<QuestionsViewModel>(parentEntry)

                LaunchedEffect(Unit) {
                    questionsViewModel.setTotalQuestions(20)
                    questionsViewModel.timer.startSilentTimer()
                }

                ExamScreen(
                    questionsViewModel = questionsViewModel,
                    onNavigateResultScreen = {
                        questionsViewModel.timer.stopSilentTimer()
                        navController.navigate(route = Screen.ResultScreen.route)
                    },
                    onTimerEnd = {
                        questionsViewModel.timer.stopSilentTimer()
                        navController.navigate(route = Screen.ResultScreen.route)
                    }
                )
            }

            composable(route = Screen.ResultScreen.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(EXAM_NAVGRAPH_ROUTE)
                }
                val questionsViewModel = viewModel<QuestionsViewModel>(parentEntry)

                ResultScreen(
                    questionsViewModel = questionsViewModel,
                    onNavigateHomeScreen = {
                        questionsViewModel.timer.resetTimer()
                        navController.navigate(route = Screen.HomeScreen.route)
                    }
                )
            }
        }
    }
}
