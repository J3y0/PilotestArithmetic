package com.jeyo.pilotestarithmetic.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeyo.pilotestarithmetic.R
import com.jeyo.pilotestarithmetic.ui.components.PilotestArithmeticHeader
import com.jeyo.pilotestarithmetic.ui.components.QuestionCountDialog
import com.jeyo.pilotestarithmetic.ui.theme.PilotestArithmeticTheme

@Composable
fun HomeScreen(onNavigateTrainingScreen: (Int) -> Unit, onNavigateExamScreen: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    PilotestArithmeticHeader(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 70.dp)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    showDialog = true
                },
                shape = MaterialTheme.shapes.medium,
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.home_training_session_button),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "right arrow",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Text(
                text = stringResource(R.string.home_training_session_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(top = 10.dp)
            )
            Text(
                text = stringResource(R.string.home_training_session_description),
                style = MaterialTheme.typography.labelMedium,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(top = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onNavigateExamScreen,
                shape = MaterialTheme.shapes.medium,

                ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.home_exam_session_button),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "right arrow",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Text(
                text = stringResource(R.string.home_exam_session_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(top = 10.dp)
            )
            Text(
                text = stringResource(R.string.home_exam_session_description),
                style = MaterialTheme.typography.labelMedium,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(top = 10.dp)
            )
        }
    }

    if (showDialog) {
        QuestionCountDialog(
            initialValue = 20,
            min = 5,
            max = 40,
            onDismiss = { showDialog = false },
            onConfirm = { count ->
                showDialog = false
                onNavigateTrainingScreen(count)
            }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "PreviewLightMode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun HomeScreenPreviewLight() {
    PilotestArithmeticTheme(darkTheme = false) {
        HomeScreen({}, {})
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "PreviewDarkMode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenPreviewDark() {
    PilotestArithmeticTheme(darkTheme = true) {
        Surface {
            HomeScreen({}, {})
        }
    }
}