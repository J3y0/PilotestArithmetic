package com.jeyo.pilotestarithmetic.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeyo.pilotestarithmetic.Equation
import com.jeyo.pilotestarithmetic.R
import com.jeyo.pilotestarithmetic.model.QuestionsViewModel
import com.jeyo.pilotestarithmetic.ui.components.AutoFocusingTextField
import com.jeyo.pilotestarithmetic.ui.components.PilotestArithmeticHeader
import com.jeyo.pilotestarithmetic.ui.components.Timer
import com.jeyo.pilotestarithmetic.ui.theme.PilotestArithmeticTheme

// Add 1 seconds so it starts at 10:00 exactly
const val TIMER_10_MINS: Long = 10 * 60 * 1000 + 1000

@Composable
fun ExamScreen(
    questionsViewModel: QuestionsViewModel,
    onNavigateResultScreen: () -> Unit,
    onTimerEnd: () -> Unit
) {
    questionsViewModel.setMode(stringResource(R.string.exam_mode))
    val totalQuestions = questionsViewModel.totalQuestions.intValue

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            PilotestArithmeticHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.exam_mode),
                    style = MaterialTheme.typography.bodyMedium
                )

                Timer(TIMER_10_MINS) {
                    onTimerEnd()
                }

                Text(
                    text = "${questionsViewModel.currentQuestion.intValue} / $totalQuestions",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            ExamQuestion(
                equation = questionsViewModel.currentEquation.value,
                isLastQuestion = questionsViewModel.isLastQuestion(),
                onNextQuestion = { isCorrect, userAnswer ->
                    if (isCorrect) {
                        questionsViewModel.incrementScore()
                    }

                    questionsViewModel.saveUserAnswer(userAnswer)
                    questionsViewModel.saveEquation()
                    if (!questionsViewModel.isLastQuestion()) {
                        // Increment and refresh equation only if the last question is not reached
                        questionsViewModel.incrementCurrentQuestion()
                        questionsViewModel.setNewEquation(questionsViewModel.equationLength.intValue)
                    } else {
                        // Reached final question
                        onNavigateResultScreen()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onNavigateResultScreen, shape = RoundedCornerShape(10.dp)) {
                Icon(imageVector = Icons.Filled.Flag, contentDescription = "Give up")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Give Up")
            }
        }
    }
}

@Composable
fun ExamQuestion(
    equation: Equation,
    isLastQuestion: Boolean,
    onNextQuestion: (isCorrect: Boolean, userAnswer: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var userAnswer by remember(equation) { mutableStateOf("") }
    var badInput by remember(equation) { mutableStateOf(false) }
    var correct by remember(equation) { mutableStateOf(false) }
    var submit by remember(equation) { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = equation.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 25.dp)
            )
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.07f))

        AutoFocusingTextField(
            value = userAnswer,
            onValueChange = {
                userAnswer = it
            },
            enabled = !submit,
            label = {
                Text(
                    text = stringResource(R.string.answer_text_field_label),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.answer_text_field_placeholder),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onDone = {
                val userAnswerInt = userAnswer.toIntOrNull()
                badInput = (userAnswerInt == null && userAnswer.isNotEmpty())
                // No errors to validate
                if (!badInput) {
                    correct = userAnswerInt == equation.solve()
                    submit = true
                }
            }
        )
        // Error message if not numbers
        if (badInput) {
            Text(
                text = stringResource(R.string.answer_text_field_error),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium,
                fontStyle = FontStyle.Italic
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (submit) {
            Button(
                onClick = { onNextQuestion(correct, userAnswer.toInt()) },
                shape = MaterialTheme.shapes.medium
            ) {
                if (isLastQuestion) {
                    Text(text = stringResource(R.string.show_score_button))
                } else {
                    Text(text = stringResource(R.string.continue_button))
                }
            }
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true, name = "PreviewLightMode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ExamScreenPreviewLight() {
    val questionsViewModel = viewModel<QuestionsViewModel>()
    PilotestArithmeticTheme(darkTheme = false) {
        Surface {
            ExamScreen(
                questionsViewModel,
                onNavigateResultScreen = {},
                onTimerEnd = {}
            )
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true, name = "PreviewDarkMode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ExamScreenPreviewDark() {
    val questionsViewModel = viewModel<QuestionsViewModel>()
    PilotestArithmeticTheme(darkTheme = true) {
        Surface {
            ExamScreen(
                questionsViewModel,
                onNavigateResultScreen = {},
                onTimerEnd = {}
            )
        }
    }
}