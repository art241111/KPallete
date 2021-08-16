package com.ger.common.settingЕnvironment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ControlPanel(
    modifier: Modifier = Modifier,
    onRun: () -> Unit,
    onStop: () -> Unit,
) {
    Column (modifier) {
        Row {
            IconButton(
                modifier = Modifier,
                onClick = onRun
            ) {
                Icon(Icons.Default.PlayArrow, "Run", tint = Color.Green, modifier = Modifier.size(40.dp))
            }

            IconButton(
                modifier = Modifier,
                onClick = onStop
            ) {
                Icon(Icons.Default.Close, "Run", tint = Color.Green, modifier = Modifier.size(40.dp))
            }
        }
    }
}