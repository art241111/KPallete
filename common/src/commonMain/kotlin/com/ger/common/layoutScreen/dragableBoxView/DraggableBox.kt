package draggableBox

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import draggableBox.data.Actions
import draggableBox.data.Focus

@Composable
fun DraggableBox(
    modifier: Modifier = Modifier,
    blockColor: Color = Color.Green,
    block: Block,
    initX: Int = 0,
    initY: Int = 0,
    actions: Actions,
    isIntersections: (Int, Int, Block) -> Boolean,
    isInPallet: (Int, Int, Block) -> Boolean
) {
    // Color settings
    val color = remember(blockColor) { mutableStateOf(blockColor) }

    // Focus settings
    val interactionSource = remember { MutableInteractionSource() }
    val focus = Focus(interactionSource)
    val borderColor = if (interactionSource.collectIsFocusedAsState().value) {
        Color.Red
    } else {
        Color.Gray
    }

    // Add-on over the move
    val localAction = actions.copy(
        isMove = { x, y ->
            color.value = if (!isIntersections(x, y, block) && isInPallet(x, y, block)) blockColor else Color.Red
            actions.isMove(x, y)
        },
        whenDragEnd = { x, y ->
            actions.whenDragEnd(x, y)
            color.value = blockColor
        }
    )

    DraggableView(
        modifier = modifier,
        actions = localAction,
        focus = focus,
        initY = initY,
        initX = initX
    ) {
        Box(
            modifier = Modifier
                .background(color = color.value, shape = RectangleShape)
                .border(block.overhang.dp, borderColor)
                .size(
                    width = (block.product.length + 2 * block.overhang).dp,
                    height = (block.product.width + 2 * block.overhang).dp
                )
        )
    }
}