package com.ger.common.layoutScreen.dragableBoxView.data

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.ger.common.data.Pallet
import com.ger.common.utils.toInt
import draggableBox.data.Actions
import draggableBox.data.CalculationHomePosition
import draggableBox.data.isInArea
import draggableBox.data.isIntersections

class CalculationLogic() {
    fun homePosition(
        areaHeight: Int,
        areaWidth: Int,
        density: Density, block: Block
    ) = CalculationHomePosition(
        height = areaHeight,
        width = areaWidth,
        block = block,
        density = density
    )

    fun actions(
        areaHeight: Int,
        areaWidth: Int,
        density: Density,
        block: Block,
    ) = Actions(
        isReturnHome = true,
        isMove = { x, y ->
            isInArea(
                x = x,
                y = y,
                areaHeight = areaHeight,
                areaWidth = areaWidth,
                block = block,
                density = density
            )
        },
    )

    fun isIntersections(x: Int, y: Int, block: Block, listOfBlocks: List<Block>, density: Density): Boolean {
        var isIntersection = false
        listOfBlocks.forEach { _block ->
            isIntersection = isIntersection || isIntersections(
                x = x,
                y = y,
                areaHeight = (_block.product.length + 2 * _block.overhang).dp.toInt(density),
                areaWidth = (_block.product.width + 2 * _block.overhang).dp.toInt(density),
                areaStartX = _block.printX,
                areaStartY = _block.printY,
                density = density,
                block = block,
            )
        }
        return isIntersection
    }

    fun isInPallet(x: Int, y: Int, block: Block, pallet: Pallet, density: Density): Boolean {
        return isInArea(
            x = x,
            y = y,
            areaHeight = pallet.length.dp.toInt(density),
            areaWidth = pallet.width.dp.toInt(density),
            block = block,
            density = density
        )
    }
}