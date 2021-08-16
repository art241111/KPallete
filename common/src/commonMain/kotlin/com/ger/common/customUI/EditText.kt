package com.ger.common.customUI

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditText(
    modifier: Modifier = Modifier,
    value: MutableState<String>,
    label: String,
    onDone: ((String) -> Unit)? = null
) {

    TextField(
        modifier = modifier
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    if (onDone != null) {
                        onDone(value.value)
                    }
                    true //true -> consumed
                } else false
            },
        value = value.value,
        onValueChange = {
            value.value = it
        },
        singleLine = true,
        label = { Text(text = label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (onDone != null) {
                    onDone(value.value)
                }
            }
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditNumber(
    modifier: Modifier = Modifier,
    value: MutableState<String>,
    label: String,
    onDone: ((String) -> Unit)? = null
) {
    val isNumber = remember { mutableStateOf(true) }
    TextField(
        modifier = modifier
            .onKeyEvent {
                when(it.key) {
                    Key.Enter -> {
                        if (onDone != null) {
                            onDone(value.value)
                        }
                        true
                    }
                    else -> false
                }
            },
        value = value.value,
        onValueChange = { newString ->
            isNumber.value = (newString.toIntOrNull() != null) || (newString == "")

            value.value = newString
        },
        isError = !isNumber.value,
        singleLine = true,
        label = { Text(text = label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (onDone != null) {
                    onDone(value.value)
                }
            }
        )
    )
}
