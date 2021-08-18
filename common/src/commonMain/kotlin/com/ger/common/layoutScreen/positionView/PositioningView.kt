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
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.ger.common.data.Pallet
import com.ger.common.data.Product
import com.ger.common.layoutScreen.dragableBoxView.DragableBoxView
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import com.ger.common.layoutScreen.dragableBoxView.data.Intersection
import com.ger.common.layoutScreen.positionView.isInAreaCheck
import com.ger.common.layoutScreen.positionView.isIntersectionCheck
import com.ger.common.utils.add
import com.ger.common.utils.change

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
            palletRemember.value, overhangRemember.value,
            productLayout.value
        ) {
            mutableStateOf(
                if (productLayout.value.layouts.lastIndex < selectedIndex.value) {
                    0
                } else {
                    selectedIndex.value
                }

            )
        }


        LazyRow(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            itemsIndexed(items = productLayout.value.layouts) { index, _ ->
                Button(
                    colors = ButtonDefaults.buttonColors(if (selectedIndex.value == index) MaterialTheme.colors.primary else Color.Transparent),
                    onClick = {
                        listOfBlocks.value = index
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
                    productLayout.value.layouts[listOfBlocks.value].add(addBlock)
                    lastFocusIndex = productLayout.value.layouts[listOfBlocks.value].value.lastIndex
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

                    productLayout.value.layouts[listOfBlocks.value].value.forEach { mainBlock ->
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
                    productLayout.value.layouts[listOfBlocks.value].add(addBlock)
                    lastFocusIndex = productLayout.value.layouts[listOfBlocks.value].value.lastIndex
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

                    productLayout.value.layouts[listOfBlocks.value].value.forEach { mainBlock ->
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


            productLayout.value.layouts[listOfBlocks.value].value.forEachIndexed { index, block ->
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
                        productLayout.value.layouts[listOfBlocks.value].change(index, addBlock)
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

                        productLayout.value.layouts[listOfBlocks.value].value.forEachIndexed { _index, mainBlock ->


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
