package com.ger.common.layoutScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.ger.common.data.Pallet
import com.ger.common.data.Product
import com.ger.common.layoutScreen.dragableBoxView.DragableBoxView
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import com.ger.common.layoutScreen.dragableBoxView.data.Intersection
import com.ger.common.utils.add
import com.ger.common.utils.change
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

@Composable
fun ColumnScope.PositioningView(
    modifier: Modifier = Modifier,
    product: Product,
    pallet: Pallet,
    overhang: Int?,
    distancesBetweenProducts: Int?,
    selectedIndex: State<Int>,
    productLayout: State<ProductLayout>,
    onSelectIndex: (newIndex: Int) -> Unit,
) {
    val productRemember = rememberUpdatedState(product)
    val palletRemember = rememberUpdatedState(pallet)
    val density = LocalDensity.current

    if (overhang != null && distancesBetweenProducts != null) {
        val overhangRemember = rememberUpdatedState(overhang)
        val distanceRemember = rememberUpdatedState(distancesBetweenProducts)


        val block = remember(productRemember.value, distanceRemember.value) {
            Block(
                width = productRemember.value.length,
                length = productRemember.value.width,
                overhang = distanceRemember.value / 2,
                height = productRemember.value.height,
                weight = productRemember.value.weight
            )
        }

        val block1 = remember(productRemember.value, distanceRemember.value) {
            Block(
                width = productRemember.value.width,
                length = productRemember.value.length,
                overhang = distanceRemember.value / 2,
                height = productRemember.value.height,
                weight = productRemember.value.weight
            )
        }

        val listOfBlocks = remember(
            productRemember.value, distanceRemember.value,
            palletRemember.value, overhangRemember.value
        ) {
            mutableStateOf(
                if (productLayout.value.layouts.lastIndex < selectedIndex.value) {
                    productLayout.value.layouts[0]
                } else {
                    productLayout.value.layouts[selectedIndex.value]
                }

            )
        }


        LazyRow(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            itemsIndexed(items = productLayout.value.layouts) { index, _ ->
                Button(
                    colors = ButtonDefaults.buttonColors(if (selectedIndex.value == index) MaterialTheme.colors.primary else Color.Transparent),
                    onClick = {
                        listOfBlocks.value = productLayout.value.layouts[index]
                        onSelectIndex(index)
                    }
                ) {
                    Text(index.toString())
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        var lastFocusIndex = remember { -1 }

        BoxWithConstraints(modifier = modifier) {
            Box(
                modifier = Modifier
                    .size(
                        width = (palletRemember.value.length + 2 * overhangRemember.value).dp,
                        height = (palletRemember.value.width + 2 * overhangRemember.value).dp
                    )
                    .background(Color.Yellow)
                    .border(overhangRemember.value.dp, Color.Gray)
            )

            DragableBoxView(
                block = block,
                onDragEnd = { x, y ->
                    val addBlock = Block(
                        width = productRemember.value.length,
                        length = productRemember.value.width,
                        height = productRemember.value.height,
                        weight = productRemember.value.weight,
                        overhang = distanceRemember.value / 2,
                        printX = x,
                        printY = y
                    )
                    listOfBlocks.add(addBlock)
                    lastFocusIndex = listOfBlocks.value.lastIndex
                },
                space = IntSize(width = this.constraints.maxWidth, height = this.constraints.maxHeight),
                alignment = Alignment.TopEnd,
                isInArea = { x, y ->
                    isInAreaCheck(
                        x = x,
                        y = y,
                        block = Block(
                            width = productRemember.value.length,
                            length = productRemember.value.width,
                            overhang = distanceRemember.value / 2,
                            printX = x,
                            printY = y,
                            height = productRemember.value.height,
                            weight = productRemember.value.weight
                        ),
                        density = density,
                        pallet = palletRemember.value,
                        palletOverhang = overhangRemember.value
                    )
                },
                isIntersection = { x, y ->
                    var intersection = Intersection()

                    listOfBlocks.value.forEach { mainBlock ->
                        val _intersection = isIntersectionCheck(
                            x = x,
                            y = y,
                            Block(
                                width = productRemember.value.length,
                                length = productRemember.value.width,
                                overhang = distanceRemember.value / 2,
                                printX = x,
                                printY = y,
                                height = productRemember.value.height,
                                weight = productRemember.value.weight
                            ),
                            density = density,
                            mainBlock = mainBlock,
                        )

                        if (_intersection.isIntersection) {
                            intersection = Intersection(
                                true,
                                _intersection.xDistance,
                                _intersection.yDistance
                            )
                        }
                    }
                    intersection
                },
            )

            DragableBoxView(
                block = block1,
                onDragEnd = { x, y ->
                    val addBlock = Block(
                        length = productRemember.value.length,
                        width = productRemember.value.width,
                        overhang = distanceRemember.value / 2,
                        printX = x,
                        printY = y,
                        height = productRemember.value.height,
                        weight = productRemember.value.weight
                    )
                    listOfBlocks.add(addBlock)
                    lastFocusIndex = listOfBlocks.value.lastIndex
                },
                isInArea = { x, y ->
                    isInAreaCheck(
                        x, y, Block(
                            length = productRemember.value.length,
                            width = productRemember.value.width,
                            overhang = distanceRemember.value / 2,
                            printX = x,
                            printY = y,
                            height = productRemember.value.height,
                            weight = productRemember.value.weight
                        ), density, palletRemember.value,
                        palletOverhang = overhangRemember.value
                    )
                },
                isIntersection = { x, y ->
                    var intersection = Intersection()

                    listOfBlocks.value.forEach { mainBlock ->
                        val _intersection = isIntersectionCheck(
                            x = x,
                            y = y,
                            Block(
                                length = productRemember.value.length,
                                width = productRemember.value.width,
                                overhang = distanceRemember.value / 2,
                                printX = x,
                                printY = y,
                                height = productRemember.value.height,
                                weight = productRemember.value.weight
                            ),
                            density = density,
                            mainBlock = mainBlock,
                        )

                        if (_intersection.isIntersection) {
                            intersection = Intersection(
                                true,
                                _intersection.xDistance,
                                _intersection.yDistance
                            )
                        }


                    }
                    intersection
                },
                space = IntSize(width = this.constraints.maxWidth, height = this.constraints.maxHeight),
                alignment = Alignment.BottomEnd,
            )


            listOfBlocks.value.forEachIndexed { index, block ->
                val isFocus = index == lastFocusIndex
                if (isFocus) lastFocusIndex = -1
                DragableBoxView(
                    block = block,
                    onDragEnd = { x, y ->

                        val addBlock = Block(
                            width = block.width,
                            length = block.length,
                            overhang = block.overhang,
                            printX = x,
                            printY = y,
                            height = productRemember.value.height,
                            weight = productRemember.value.weight
                        )
                        listOfBlocks.change(index, addBlock)


                    },
                    isInArea = { x, y ->
                        isInAreaCheck(
                            x, y, Block(
                                width = productRemember.value.length,
                                length = productRemember.value.width,
                                overhang = distanceRemember.value / 2,
                                printX = x,
                                printY = y,
                                height = productRemember.value.height,
                                weight = productRemember.value.weight
                            ), density, palletRemember.value,
                            palletOverhang = overhangRemember.value
                        )
                    },
                    isIntersection = { x, y ->
                        var intersection = Intersection()

                        listOfBlocks.value.forEachIndexed { _index, mainBlock ->


                            if (_index != index) {
                                val _intersection = isIntersectionCheck(
                                    x = x,
                                    y = y,
                                    block = block,
                                    density = density,
                                    mainBlock = mainBlock,
                                )

                                if (_intersection.isIntersection) {
                                    intersection = Intersection(
                                        true,
                                        _intersection.xDistance,
                                        _intersection.yDistance
                                    )
                                }
                            }


                        }
                        intersection
                    },
                    initX = block.printX,
                    initY = block.printY,
                    space = IntSize(width = this.constraints.maxWidth, height = this.constraints.maxHeight),
                    isFocus = isFocus
                )
            }
        }
    }
}
