package com.ger.common.addPoint

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ger.common.customUI.FocusableButton
import com.ger.common.data.Point
import com.ger.common.strings.S

@Composable
fun AddPoint (
    modifier: Modifier = Modifier,
    point: State<Point>,
    updatePosition: () ->Unit,
    onBack: () -> Unit,
) {
    Box (modifier.fillMaxSize()) {
        Card(Modifier.align(Alignment.Center)) {
            Column {
                Row {
                    Text(point.value.toString())

                    FocusableButton(
                        onClick = updatePosition,
                        text = S.strings.update
                    )
                }

                FocusableButton(
                    onClick = {onBack()},
                    text = S.strings.addPoint
                )
            }
        }
    }
}