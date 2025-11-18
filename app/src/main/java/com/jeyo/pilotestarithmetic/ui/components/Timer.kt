package com.jeyo.pilotestarithmetic.ui.components

import android.os.CountDownTimer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import java.util.Locale

@Composable
fun Timer(waitingTimeMillis: Long, onFinish: () -> Unit) {
    val timeMillis = remember {
        mutableLongStateOf(waitingTimeMillis)
    }
    val timeMinutes: Long = timeMillis.longValue / (60 * 1000)
    val timeSeconds: Long = (timeMillis.longValue / 1000) % 60

    val countDownTimer =
        object : CountDownTimer(waitingTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeMillis.longValue = millisUntilFinished
            }

            override fun onFinish() = onFinish()
        }

    DisposableEffect(timeMillis) {
        countDownTimer.start()
        onDispose {
            countDownTimer.cancel()
        }
    }

    Text(
        text = String.format(locale = Locale.getDefault(), "%02d:%02d", timeMinutes, timeSeconds),
        style = MaterialTheme.typography.bodyLarge
    )
}