package draggableBox.data

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import com.ger.common.utils.toInt

fun isInArea(
    x: Int,
    y: Int,
    areaWidth: Int,
    areaHeight: Int,
    areaStartX: Int = 0,
    areaStartY: Int = 0,
    block: Block,
    density: Density
): Boolean {
    val blockOverhang = block.overhang.dp.toInt(density)
    val blockLength = block.product.length.dp.toInt(density)
    val blockWidth = block.product.width.dp.toInt(density)

    val area = Area(
        areaWidth = areaWidth,
        areaHeight = areaHeight,
        areaStartX = areaStartX,
        areaStartY = areaStartY
    )
    val isTopLeftInArea = area.isPointInArea(
        pointX = x,
        pointY = y,
    )

    val isTopRightInArea = area.isPointInArea(
        pointX = x + blockWidth + blockOverhang * 2,
        pointY = y,
    )

    val isBottomLeftInArea = area.isPointInArea(
        pointX = x,
        pointY = y + blockLength + blockOverhang * 2,
    )

    val isBottomRightInArea = area.isPointInArea(
        pointX = x + blockWidth + blockOverhang * 2,
        pointY = y + blockLength + blockOverhang * 2,
    )

    return isTopLeftInArea && isTopRightInArea && isBottomLeftInArea && isBottomRightInArea
}

fun isIntersections(
    x: Int,
    y: Int,
    areaWidth: Int,
    areaHeight: Int,
    areaStartX: Int = 0,
    areaStartY: Int = 0,
    block: Block,
    density: Density
): Boolean {
    val blockOverhang = block.overhang.dp.toInt(density)
    val blockLength = block.product.length.dp.toInt(density)
    val blockWidth = block.product.width.dp.toInt(density)

    val area = Area(
        areaWidth = areaWidth,
        areaHeight = areaHeight,
        areaStartX = areaStartX,
        areaStartY = areaStartY
    )
    val isTopLeftInArea = area.isPointInArea(
        pointX = x,
        pointY = y,
    )

    val isTopRightInArea = area.isPointInArea(
        pointX = x + blockWidth + blockOverhang * 2,
        pointY = y,
    )

    val isBottomLeftInArea = area.isPointInArea(
        pointX = x,
        pointY = y + blockLength + blockOverhang * 2,
    )

    val isBottomRightInArea = area.isPointInArea(
        pointX = x + blockWidth + blockOverhang * 2,
        pointY = y + blockLength + blockOverhang * 2,
    )

    return isTopLeftInArea || isTopRightInArea || isBottomLeftInArea || isBottomRightInArea
}

private class Area(
    private val areaWidth: Int,
    private val areaHeight: Int,
    private val areaStartX: Int = 0,
    private val areaStartY: Int = 0,
) {
    fun isPointInArea(
        pointX: Int,
        pointY: Int,
    ): Boolean {
        return (pointX <= (areaWidth + areaStartX) && pointX >= areaStartX) &&
                (pointY <= (areaHeight + areaStartY) && pointY >= areaStartY)
    }
}