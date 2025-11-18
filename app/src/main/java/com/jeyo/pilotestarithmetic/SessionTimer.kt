package com.jeyo.pilotestarithmetic

import android.os.SystemClock
import java.util.Locale

class SessionTimer {
    private var startTime: Long? = null

    // Elapsed time in milliseconds
    var elapsedTime: Long? = null
        private set

    private var startPauseTime: Long? = null
    private var elapsedPauseTime: Long? = 0
    private var isPaused: Boolean = false

    fun startSilentTimer() {
        startTime = SystemClock.uptimeMillis()
    }

    fun pauseSilentTimer() {
        if (!isPaused) {
            startPauseTime = SystemClock.uptimeMillis()
            isPaused = true
        }
    }

    fun unpauseSilentTimer() {
        startPauseTime?.let { start ->
            val currentPauseTime = elapsedPauseTime ?: 0
            elapsedPauseTime = currentPauseTime + (SystemClock.uptimeMillis() - start)
            isPaused = false
        }
    }

    fun stopSilentTimer() {
        startTime?.let { start ->
            val pauseTime = elapsedPauseTime ?: 0
            elapsedTime = SystemClock.uptimeMillis() - start - pauseTime
        }
    }

    fun resetTimer() {
        startTime = null
        elapsedTime = null
        startPauseTime = null
        elapsedPauseTime = null
        isPaused = false
    }

    fun getTimerSeconds(): Long {
        val elapsedTime = elapsedTime ?: 0
        return (elapsedTime / 1000) % 60
    }

    fun getTimerMinutes(): Long {
        val elapsedTime = elapsedTime ?: 0
        return (elapsedTime / (60 * 1000)) % 60
    }

    override fun toString(): String {
        return String.format(
            locale = Locale.getDefault(),
            "%02d:%02d",
            getTimerMinutes(),
            getTimerSeconds()
        )
    }
}