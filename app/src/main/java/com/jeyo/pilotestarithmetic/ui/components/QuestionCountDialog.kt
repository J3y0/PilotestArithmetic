package com.jeyo.pilotestarithmetic.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jeyo.pilotestarithmetic.ui.theme.PilotestArithmeticTheme

@Composable
fun QuestionCountDialog(
    initialValue: Int = 20,
    min: Int = 5,
    max: Int = 40,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var value by remember { mutableIntStateOf(initialValue) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            tonalElevation = 5.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(min = 280.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Choose number of questions",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.height(20.dp))

                // Number display
                Text(
                    text = "$value questions",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(12.dp))

                Slider(
                    value = value.toFloat(),
                    onValueChange = { value = it.toInt() },
                    valueRange = min.toFloat()..max.toFloat(),
                )

                Spacer(Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.errorContainer,
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.errorContainer
                        )
                    }
                    TextButton(
                        onClick = { onConfirm(value) },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "Start", style = MaterialTheme.typography.labelLarge)
                    }
                }
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
fun QuestionCountDialogLight() {
    PilotestArithmeticTheme(darkTheme = false) {
        QuestionCountDialog(20, 5, 40, {}, {})
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "PreviewDarkMode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun QuestionCountDialogDark() {
    PilotestArithmeticTheme(darkTheme = true) {
        Surface {
            QuestionCountDialog(20, 5, 40, {}, {})
        }
    }
}