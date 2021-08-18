package com.ger.common.layoutScreen.dragableBoxView

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import com.ger.common.layoutScreen.dragableBoxView.data.Intersection
import com.ger.common.utils.toInt
import kotlin.math.roundToInt

/**
 *
 * @author Artem Gerasimov (gerasimov.av.dev@gmail.com)
 */
@OptIn(ExperimentalComposeUiApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun DragableBoxView(
    modifier: Modifier = Companion,
    blockColor: Color = Color.Green,
    block: Block,
    onDragEnd: (x: Int, y: Int) -> Unit,
    isInArea: (x: Int, y: Int) -> Boolean,
    isIntersection: (x: Int, y: Int) -> Intersection,
    initX: Int = 0,
    initY: Int = 0,
    space: IntSize,
    alignment: Alignment? = null,
    isFocus: Boolean = false,
) {
    val density = LocalDensity.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val borderColor = if (interactionSource.collectIsFocusedAsState().value) {
            Color.Red
        } else {
            Color.Gray
        }


        val offsetX = remember(initX) { mutableStateOf(initX) }
        val offsetY = remember(initY) { mutableStateOf(initY) }

        val homeX = remember(initX) { mutableStateOf(initX) }
        val homeY = remember(initY) { mutableStateOf(initY) }

        LaunchedEffect(block, space, Unit) {
            val blockWidth = block.length.dp.toInt(density)
            val blockHeight = block.width.dp.toInt(density)
            val blockOffset = 2 * block.overhang.dp.toInt(density)

            val spaceCenter = ((space.height - blockWidth - blockHeight - blockOffset) / 2)

            val _offsetX = space.width - blockWidth - blockOffset
            val _offsetY =
                if (alignment == Alignment.TopEnd) spaceCenter
                else (blockWidth + blockOffset + 10.dp.toInt(density) + spaceCenter)

            if (initX == 0 && initY == 0 && alignment != null) {
                offsetX.value = _offsetX
                offsetY.value = _offsetY
//
                homeX.value = _offsetX
                homeY.value = _offsetY
            }
        }

        val color = remember { mutableStateOf(blockColor) }
        val buttonFocusRequester = remember { FocusRequester() }
        LaunchedEffect(isFocus) {
            if (isFocus) buttonFocusRequester.requestFocus()
        }

        fun move(
            shiftX: Int = 0,
            shiftY: Int = 0,
        ) {
            val newPositionX = offsetX.value + shiftX
            val newPositionY = offsetY.value + shiftY
//            val intersection = isIntersection(newPositionX, newPositionY)
//            val isNotInteraction = !intersection.isIntersection
//            if (isNotInteraction && isInArea(newPositionX, newPositionY)) {
                offsetX.value = newPositionX
                offsetY.value = newPositionY
//            }
        }

        Button(
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(backgroundColor = color.value),
            shape = RectangleShape,
            border = BorderStroke(block.overhang.dp, borderColor),
            onClick = { buttonFocusRequester.requestFocus() },
            modifier = Modifier
                .onPreviewKeyEvent {
                    when (it.key) {
                        Key.DirectionLeft -> move(shiftX = -1)
                        Key.DirectionRight -> move(shiftX = 1)
                        Key.DirectionUp -> move(shiftY = -1)
                        Key.DirectionDown -> move(shiftY = 1)
                    }
                    false
                }
                .focusRequester(buttonFocusRequester)
                .focusable(interactionSource = interactionSource)
                .offset {
                    IntOffset(
                        offsetX.value,
                        offsetY.value
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { buttonFocusRequester.requestFocus() },
                        onDragEnd = {

//                            if (isInArea(offsetX.value, offsetY.value)) {
                                onDragEnd(offsetX.value, offsetY.value)
//                            } else {
//                                offsetX.value = homeX.value
//                                offsetY.value = homeY.value
//                                color.value = blockColor
//                            }

                            if ((initX == 0) || (initY == 0)) {
                                offsetX.value = homeX.value
                                offsetY.value = homeY.value
                                color.value = blockColor
                            }
                        }
                    ) { change, dragAmount ->
                        change.consumeAllChanges()
                        move(
                            (dragAmount.x).roundToInt(),
                            (dragAmount.y).roundToInt()
                        )
                    }
                }
                .size(
                    width = (block.length + 2 * block.overhang).dp,
                    height = (block.width + 2 * block.overhang).dp
                )
        ) {}
    }
}