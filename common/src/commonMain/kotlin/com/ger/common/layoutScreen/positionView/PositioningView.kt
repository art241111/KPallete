package com.ger.common.layoutScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ger.common.data.Pallet
import com.ger.common.data.Product
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import com.ger.common.layoutScreen.dragableBoxView.data.CalculationLogic
import com.ger.common.utils.add
import com.ger.common.utils.toGraphicInt
import draggableBox.DraggableBox

@Composable
fun ColumnScope.PositioningView(
    modifier: Modifier = Modifier,
    product: Product,
    pallet: Pallet,
    overhang: Int?,
    distancesBetweenProducts: Int?,
    listOfBlocks: MutableState<List<Block>>,
) {
    val productRemember = rememberUpdatedState(product)
    val palletRemember = rememberUpdatedState(pallet)

    if (overhang != null && distancesBetweenProducts != null) {
        val overhangRemember = rememberUpdatedState(overhang)
        val distanceRemember = rememberUpdatedState(distancesBetweenProducts)

        val block = remember(productRemember.value, distanceRemember.value) {
            Block(
                product = productRemember.value,
                overhang = distanceRemember.value / 2,
            )
        }

        BoxWithConstraints(modifier = modifier.border(1.dp, Color.Red)) {
            val density = LocalDensity.current
            val calculationLogic = remember (maxWidth, maxHeight) { CalculationLogic() }
            val homePosition =
                remember(block, maxWidth, maxHeight) {
                    calculationLogic.homePosition(
                        maxHeight.toGraphicInt(density),
                        maxWidth.toGraphicInt(density),
                        density,
                        block
                    )
                }
            val actions = remember(block, maxWidth, maxHeight, density) {
                calculationLogic.actions(
                    maxHeight.toGraphicInt(density),
                    maxWidth.toGraphicInt(density),
                    density,
                    block,
                )
            }
            val isIntersections: (Int, Int, Block) -> Boolean = remember(listOfBlocks, density) {
                { x, y, _block ->
                    calculationLogic.isIntersections(x, y, _block, listOfBlocks.value, density)
                }
            }
            val isInPallet: (Int, Int, Block) -> Boolean = remember(pallet, density) {
                { x, y, _block ->
                    calculationLogic.isInPallet(x, y, _block, pallet, density)
                }
            }

            // Pallet
            Box(
                modifier = Modifier
                    .size(
                        width = (palletRemember.value.length + 2 * overhangRemember.value).dp,
                        height = (palletRemember.value.width + 2 * overhangRemember.value).dp
                    )
                    .background(Color.Yellow)
                    .border(overhangRemember.value.dp, Color.Gray)
            )

            // Home box
            DraggableBox(
                block = block,
                initX = homePosition.first - 40,
                initY = homePosition.second - (block.product.height + block.overhang + 10).toGraphicInt(density),
                actions = actions.copy(
                    whenDragEnd = { x, y ->
                        listOfBlocks.add(block.copy(x = x, y = y))
                    }
                ),
                isIntersections = isIntersections,
                isInPallet = isInPallet
            )

            // Home box
            DraggableBox(
                block = block,
                initX = homePosition.first  - 20,
                initY = homePosition.second + (block.product.width + block.overhang + 10).toGraphicInt(density),
                actions = actions.copy(
                    whenDragEnd = { x, y ->
                        listOfBlocks.add(block.copy(x = x, y = y))
                    }
                ),
                isIntersections = isIntersections,
                isInPallet = isInPallet
            )

            listOfBlocks.value.forEachIndexed { index, _block ->
                // Home box
                DraggableBox(
                    block = _block,
                    initX = _block.x,
                    initY = _block.y,
                    actions = actions.copy(
                        isReturnHome = false,
                        whenDragEnd = { x, y ->
                            listOfBlocks.value[index].x = x
                            listOfBlocks.value[index].y = y
                        }
                    ),
                    isIntersections = isIntersections,
                    isInPallet = isInPallet
                )
            }
        }
    }
}
