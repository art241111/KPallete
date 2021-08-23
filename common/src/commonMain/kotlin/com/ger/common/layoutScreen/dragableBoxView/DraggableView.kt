package draggableBox

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import draggableBox.data.Actions
import draggableBox.data.Focus
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun DraggableView(
    modifier: Modifier = Modifier,
    actions: Actions = Actions(),
    initX: Int = 0,
    initY: Int = 0,
    focus: Focus = Focus(),
    content: @Composable BoxScope.() -> Unit
) {
    val buttonFocusRequester = remember { FocusRequester() }
    LaunchedEffect(focus.isFocus) {
        if (focus.isFocus) buttonFocusRequester.requestFocus()
    }

    val offsetX = remember { mutableStateOf(initX) }
    val offsetY = remember { mutableStateOf(initY) }
    val homeOffsetX = remember (initX) { offsetX.value = initX
        mutableStateOf(initX) }
    val homeOffsetY = remember (initY) { offsetY.value = initY
        mutableStateOf(initY) }

    fun move(shiftX: Int = 0, shiftY: Int = 0) {
        val newPositionX = offsetX.value + shiftX
        val newPositionY = offsetY.value + shiftY

        if (actions.isMove(newPositionX, newPositionY)) {
            offsetX.value = newPositionX
            offsetY.value = newPositionY
        }
    }

    Surface(
        onClick = { buttonFocusRequester.requestFocus() },
        interactionSource = focus.interactionSource,
        color = Color.Transparent,
        modifier = modifier
            .onPreviewKeyEvent {
                if (!actions.isReturnHome) {
                    when (it.key) {
                        Key.DirectionLeft -> move(shiftX = -1)
                        Key.DirectionRight -> move(shiftX = 1)
                        Key.DirectionUp -> move(shiftY = -1)
                        Key.DirectionDown -> move(shiftY = 1)
                    }
                    false
                } else false
            }
            .focusRequester(buttonFocusRequester)
            .focusable(interactionSource = focus.interactionSource)
            .offset {
                IntOffset(offsetX.value, offsetY.value)
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { buttonFocusRequester.requestFocus() },
                    onDragEnd = {
                        actions.whenDragEnd(offsetX.value, offsetY.value)

                        if (actions.isReturnHome) {
                            offsetX.value = homeOffsetX.value
                            offsetY.value = homeOffsetY.value
                        }
                    }
                ) { change, dragAmount ->
                    change.consumeAllChanges()
                    move((dragAmount.x).roundToInt(), (dragAmount.y).roundToInt())
                }
            }
    ) {
        Box {
            content()
        }
    }
}