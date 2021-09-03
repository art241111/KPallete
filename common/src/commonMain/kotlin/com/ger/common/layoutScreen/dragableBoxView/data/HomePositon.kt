package draggableBox.data

import androidx.compose.ui.unit.Density
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import com.ger.common.utils.toGraphicInt


fun CalculationHomePosition(
    height: Int,
    width: Int,
    block: Block,
    density: Density,
): Pair<Int, Int> {
    val blockWidth = block.product.length.toGraphicInt(density)
    val blockHeight = block.product.width.toGraphicInt(density)
    val blockOffset = 2 * block.overhang.toGraphicInt(density)

    val spaceCenter = ((height - blockWidth - blockHeight - blockOffset) / 2)

    return Pair(width - blockWidth - blockOffset, spaceCenter)
}