package com.ger.common.layoutScreen.positionView

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.ger.common.data.Pallet
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import com.ger.common.layoutScreen.dragableBoxView.data.Intersection
import com.ger.common.utils.toInt


fun isInAreaCheck(x: Int, y: Int, block: Block, density: Density, pallet: Pallet, palletOverhang: Int): Boolean {
    val blockOverhang = block.overhang.dp.toInt(density)
    val blockLength = block.length.dp.toInt(density)
    val blockWidth = block.width.dp.toInt(density)

    val newX = x + blockOverhang
    val newY = y + blockOverhang

    val areaLength = (pallet.length + 2 * palletOverhang).dp.toInt(density)
    val areaWidth = (pallet.width + 2 * palletOverhang).dp.toInt(density)

    val isCross1 = ((newX + blockLength) < areaLength) && (((0 - blockWidth) < newY) && (newY < areaWidth))
    val isCross2 = ((newY + blockWidth) < areaWidth) && (((0 - blockLength) < newX) && (newX < areaLength))
    val isCross3 = (newX > 0) && (((0 - blockWidth) < newY) && (newY < areaWidth))
    val isCross4 = (newY > 0) && (((0 - blockLength) < newX) && (newX < areaLength))

    return isCross1 && isCross2 && isCross3 && isCross4
}

fun isIntersectionCheck(x: Int, y: Int, block: Block, density: Density, mainBlock: Block): Intersection {
    val blockOverhang = block.overhang.dp.toInt(density)
    val blockLength = block.length.dp.toInt(density) + (blockOverhang * 2)
    val blockWidth = block.width.dp.toInt(density) + (blockOverhang * 2)

    val mainBlockOverhang = mainBlock.overhang.dp.toInt(density)
    val mainBlockLength = (mainBlock.length).dp.toInt(density) + 2 * mainBlockOverhang
    val mainBlockWidth = (mainBlock.width).dp.toInt(density) + 2 * mainBlockOverhang

    val isCross1 =
        (x < (mainBlockLength + mainBlock.printX)) && (((mainBlock.printY - blockWidth) < y) && (y < (mainBlockWidth + mainBlock.printY)))
    val isCross2 =
        (y < (mainBlockWidth + mainBlock.printY)) && (((mainBlock.printX - blockLength) < x) && (x < (mainBlockLength + mainBlock.printX)))
    val isCross3 =
        ((x + blockLength) > mainBlock.printX) && (((mainBlock.printY - blockWidth) < y) && (y < (mainBlockWidth + mainBlock.printY)))
    val isCross4 =
        ((y + blockWidth) > mainBlock.printY) && (((mainBlock.printX - blockLength) < x) && (x < (mainBlockLength + mainBlock.printX)))

    var xDistance = 0
    var yDistance = 0

    val isIntersection = isCross1 && isCross2 && isCross3 && isCross4

    if (isCross1 && !isCross2 && !isCross3 && !isCross4) {
        xDistance = (mainBlockLength + mainBlock.printX - x).toInt()
    }

    if (!isCross1 && isCross2 && !isCross3 && !isCross4) {
        yDistance = -1
    }

    if (!isCross1 && !isCross2 && isCross3 && !isCross4) {
        xDistance = -1
    }

    if (!isCross1 && !isCross2 && !isCross3 && isCross4) {
        yDistance = 1
    }
    return Intersection(
        isIntersection = isIntersection,
        xDistance = xDistance,
        yDistance = yDistance
    )
}