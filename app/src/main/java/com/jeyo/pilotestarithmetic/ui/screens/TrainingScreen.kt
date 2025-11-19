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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
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
import com.jeyo.pilotestarithmetic.ui.theme.PilotestArithmeticTheme

@Composable
fun TrainingScreen(
    questionsViewModel: QuestionsViewModel,
    onNavigateResultScreen: () -> Unit
) {
    questionsViewModel.setMode(stringResource(R.string.training_mode))
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.training_mode),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${questionsViewModel.currentQuestion.intValue} / $totalQuestions",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            TrainingQuestion(
                equation = questionsViewModel.currentEquation.value,
                isLastQuestion = questionsViewModel.isLastQuestion(),
                onAnswer = { isCorrect, userAnswer ->
                    questionsViewModel.timer.pauseSilentTimer()
                    if (isCorrect) {
                        questionsViewModel.incrementScore()
                    }
                    questionsViewModel.saveUserAnswer(userAnswer)
                    questionsViewModel.saveEquation()
                },
                onNextQuestion = {
                    questionsViewModel.timer.unpauseSilentTimer()

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
fun TrainingQuestion(
    equation: Equation,
    isLastQuestion: Boolean,
    onAnswer: (isCorrect: Boolean, userAnswer: Int) -> Unit,
    onNextQuestion: () -> Unit,
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

        Spacer(modifier = Modifier.height(30.dp))

        val style: TextFieldColors = if (submit && correct) {
            OutlinedTextFieldDefaults.colors(
                disabledLabelColor = MaterialTheme.colorScheme.tertiary,
                disabledBorderColor = MaterialTheme.colorScheme.tertiary,
                disabledTextColor = MaterialTheme.colorScheme.tertiary,
            )
        } else if (submit) {
            OutlinedTextFieldDefaults.colors(
                disabledLabelColor = MaterialTheme.colorScheme.error,
                disabledBorderColor = MaterialTheme.colorScheme.error,
                disabledTextColor = MaterialTheme.colorScheme.error,
            )
        } else {
            OutlinedTextFieldDefaults.colors()
        }
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
            colors = style,
            onDone = {
                badInput = (userAnswer.toIntOrNull() == null && userAnswer.isEmpty())
                // No errors to validate
                if (!badInput) {
                    val userAnswerInt = userAnswer.toInt()
                    correct = userAnswerInt == equation.solve()
                    submit = true
                    onAnswer(correct, userAnswerInt)
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
            if (!correct) {
                Text(
                    text = stringResource(R.string.real_answer_comment_fail),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )
                Text(
                    text = "${stringResource(R.string.the_answer_was_text)} ${equation.solve()}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                Text(
                    text = stringResource(R.string.real_answer_comment_nice),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = onNextQuestion,
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
fun TrainingScreenPreviewLight() {
    val questionsViewModel = viewModel<QuestionsViewModel>()
    PilotestArithmeticTheme(darkTheme = false) {
        Surface {
            TrainingScreen(
                questionsViewModel,
                onNavigateResultScreen = {})
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true, name = "PreviewDarkMode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TrainingScreenPreviewDark() {
    val questionsViewModel = viewModel<QuestionsViewModel>()
    PilotestArithmeticTheme(darkTheme = true) {
        Surface {
            TrainingScreen(
                questionsViewModel,
                onNavigateResultScreen = {})
        }
    }
}