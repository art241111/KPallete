package draggableBox.data

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import com.ger.common.utils.toInt


fun CalculationHomePosition(
    height: Int,
    width: Int,
    block: Block,
    density: Density,
): Pair<Int, Int> {
    val blockWidth = block.product.length.dp.toInt(density)
    val blockHeight = block.product.width.dp.toInt(density)
    val blockOffset = 2 * block.overhang.dp.toInt(density)

    val spaceCenter = ((height - blockWidth - blockHeight - blockOffset) / 2)

    return Pair(width - blockWidth - blockOffset, spaceCenter)
}