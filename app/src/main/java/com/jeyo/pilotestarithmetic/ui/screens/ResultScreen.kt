package com.jeyo.pilotestarithmetic.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeyo.pilotestarithmetic.R
import com.jeyo.pilotestarithmetic.model.QuestionsViewModel
import com.jeyo.pilotestarithmetic.ui.components.PilotestArithmeticHeader
import com.jeyo.pilotestarithmetic.ui.theme.PilotestArithmeticTheme
import kotlin.random.Random

data class QuestionResult(
    val questionNumber: Int,
    val equation: String,
    val userAnswer: Int,
    val solution: Int
)

@Composable
fun ResultScreen(
    questionsViewModel: QuestionsViewModel,
    onNavigateHomeScreen: () -> Unit
) {
    val userAnswers = questionsViewModel.userAnswers

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(
                top = 280.dp,
                bottom = 30.dp,
                start = 15.dp,
                end = 15.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(questionsViewModel.equations) { idx, eq ->
                val result = QuestionResult(
                    questionNumber = idx + 1,
                    equation = eq.toString(),
                    userAnswer = userAnswers[idx],
                    solution = eq.solve()
                )
                val isCorrect = result.solution == userAnswers[idx]

                val colors: CardColors = if (isCorrect) {
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = colors,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                    ) {
                        Text(
                            text = "${stringResource(R.string.question)} ${result.questionNumber}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Equation:",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp, bottom = 5.dp)
                        )

                        Text(
                            text = result.equation,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 15.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (!isCorrect) {
                                Row(
                                    modifier = Modifier
                                        .background(
                                            color = Color(0x16000000),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .padding(7.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.result_your_answer_text),
                                        style = MaterialTheme.typography.labelMedium,
                                    )
                                    Text(
                                        text = " ${result.userAnswer}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // keep SpaceBetween when answer is correct
                            Spacer(Modifier.weight(1f))

                            Row(
                                modifier = Modifier
                                    .background(
                                        color = Color(0x16000000),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(7.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.result_the_solution_text),
                                    style = MaterialTheme.typography.labelMedium,
                                )
                                Text(
                                    text = " ${result.solution}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.TopCenter)
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PilotestArithmeticHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${questionsViewModel.getMode()} session completed in: "
                )

                Text(
                    text = questionsViewModel.timer.toString()
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${stringResource(R.string.score)}: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "${questionsViewModel.score.intValue}/${questionsViewModel.userAnswers.size} !",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = onNavigateHomeScreen,
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(R.string.home_button)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "PreviewLightMode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ResultScreenLightPreview() {
    val questionsViewModel = viewModel<QuestionsViewModel>()
    questionsViewModel.setMode(stringResource(R.string.training_mode))
    questionsViewModel.setTotalQuestions(20)

    for (i in 0 until 20) {
        questionsViewModel.setNewEquation(10)
        if (i.mod(3) == 0) {
            questionsViewModel.saveUserAnswer(questionsViewModel.currentEquation.value.solve())
            questionsViewModel.incrementScore()

        } else {
            questionsViewModel.saveUserAnswer(Random.nextInt(until = 100))
        }
        questionsViewModel.saveEquation()
    }

    PilotestArithmeticTheme(darkTheme = false) {
        ResultScreen(
            questionsViewModel
        ) {}
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "PreviewDarkMode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ResultScreenDarkPreview() {
    val questionsViewModel = viewModel<QuestionsViewModel>()
    questionsViewModel.setMode(stringResource(R.string.exam_mode))
    questionsViewModel.setTotalQuestions(20)

    for (i in 0 until 20) {
        questionsViewModel.setNewEquation(10)
        if (i.mod(3) == 0) {
            questionsViewModel.saveUserAnswer(questionsViewModel.currentEquation.value.solve())
            questionsViewModel.incrementScore()

        } else {
            questionsViewModel.saveUserAnswer(Random.nextInt(until = 100))
        }
        questionsViewModel.saveEquation()
    }

    PilotestArithmeticTheme(darkTheme = true) {
        Surface {
            ResultScreen(
                questionsViewModel
            ) {}
        }
    }
}