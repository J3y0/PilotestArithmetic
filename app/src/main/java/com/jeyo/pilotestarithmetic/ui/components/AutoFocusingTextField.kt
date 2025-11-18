package com.jeyo.pilotestarithmetic.ui.components

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AutoFocusingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    onDone: (KeyboardActionScope.() -> Unit)? = null
) {
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = value,
        label = label,
        placeholder = placeholder,
        onValueChange = onValueChange,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
            showKeyboardOnFocus = true
        ),
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            onDone?.invoke(this)
            focusRequester.freeFocus()

        }),
        modifier = Modifier.focusRequester(focusRequester),
        colors = colors
    )

    LaunchedEffect(enabled) {
        if (enabled) {
            focusRequester.requestFocus()
        }
    }
}