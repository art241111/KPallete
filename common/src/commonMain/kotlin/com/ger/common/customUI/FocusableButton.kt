package com.ger.common.customUI

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FocusableButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    val interactionSource = remember { MutableInteractionSource() }

    val colors = ButtonDefaults.buttonColors(
        backgroundColor = if (interactionSource.collectIsFocusedAsState().value) {
            MaterialTheme.colors.secondary
        } else {
            MaterialTheme.colors.primary
        }
    )

    Button(
        interactionSource = interactionSource,
        onClick = {
            onClick()
        },
        colors = colors,
        modifier = modifier
            .onPreviewKeyEvent {
                if (
                    it.key == Key.Enter ||
                    it.key == Key.Spacebar
                ) {
                    onClick()
                }
                false
            }
            .focusable(interactionSource = interactionSource),
    ) {
        Text(text)
    }
}