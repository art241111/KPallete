package com.ger.common.layoutScreen

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.ger.common.data.Pallet
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import com.ger.common.utils.toInt

class ProductLayout(
    pallet: Pallet,
    block: Block,
    density: Density,
    overhang: Int
) {
    val layouts: List<List<Block>>
    val optimalListIndex: Int
    val _pallet = pallet.copy(width = pallet.width + 2 * overhang, length = pallet.length + 2 * overhang)

    init {
        val set = setOf<List<Block>>(
            straight(_pallet, block, density, isRotated = false),
            straight(_pallet, block.copy(width = block.length, length = block.width), density, isRotated = true),
            crossLayout(_pallet, block, density, isRotated = false),//2
            crossLayout(_pallet, block.copy(width = block.length, length = block.width), density, isRotated = true),
            crossLayoutRotated(_pallet, block, density, isRotated = false),
            crossLayoutRotated(
                _pallet,
                block.copy(width = block.length, length = block.width),
                density,
                isRotated = true
            ), //5
            listOf()
        )

        layouts = set.toList()

        var maxBlock = 0
        var optimalIndex = 0
        layouts.forEachIndexed { index, list ->
            if (list.size > maxBlock) {
                maxBlock = list.size
                optimalIndex = index
            }
        }
        optimalListIndex = optimalIndex
    }

    /**
     * Прямая раскладка, при которой все идут в одном направлении.
     */
    fun straight(pallet: Pallet, block: Block, density: Density, isRotated: Boolean): List<Block> {
        val list = mutableListOf<Block>()

        val blockLength = (block.length + 2 * block.overhang)
        val blockWidth = (block.width + 2 * block.overhang)

        val lengthCount = pallet.length / blockLength
        val widthCount = pallet.width / blockWidth

        for (i in 0 until lengthCount.toInt()) {
            for (j in 0 until widthCount.toInt()) {
                list.add(
                    block.copy(
                        printX = (blockLength.dp.toInt(density)) * i,
                        printY = (blockWidth.dp.toInt(density)) * j,
                        x = blockLength,
                        y = blockWidth * j,
                        isRotated  = isRotated
                    )
                )
            }
        }

        return list
    }

    /**
     * Раскладка, в которой блоки могут лежать в разных направлениях.
     */
    private fun crossLayout(pallet: Pallet, block: Block, density: Density, isRotated: Boolean): List<Block> {
        val list = mutableListOf<Block>()

        val palletLength = pallet.length
        val palletWidth = pallet.width

        val blockLength = (block.length + 2 * block.overhang)
        val blockWidth = (block.width + 2 * block.overhang)

        val widthCount = palletWidth / blockWidth
        val widthCountInverted = palletWidth / blockLength

        val optimalChoice = optimalBlocks(palletLength, palletWidth, blockLength, blockWidth)

        // Создаем все блоки
        list.addAll(
            addRotatedBlocks(
                block,
                blockLength,
                blockWidth,
                optimalChoice,
                widthCountInverted.toInt(),
                density,
                !isRotated
            )
        )

        val lengthCount = (palletLength - optimalChoice * blockWidth) / blockLength
        val lengthShift = optimalChoice * blockWidth
        list.addAll(
            printBlocks(
                block = block,
                blockLength = blockLength,
                blockWidth = blockWidth,
                density = density,
                xCount = lengthCount.toInt(),
                yCount = widthCount.toInt(),
                xShift = lengthShift,
                isRotated = isRotated
            )
        )

        return list
    }

    /**
     * Раскладка, в которой блоки могут лежать в разных направлениях.
     */
    private fun crossLayoutRotated(pallet: Pallet, block: Block, density: Density, isRotated: Boolean): List<Block> {
        val list = mutableListOf<Block>()

        val palletLength = pallet.length
        val palletWidth = pallet.width

        val blockLength = (block.length + 2 * block.overhang)
        val blockWidth = (block.width + 2 * block.overhang)

        val lengthCount = palletLength / blockLength
        val lengthCountInverted = palletLength / blockWidth

        val optimalChoice =
            optimalBlocks(palletWidth, palletLength, blockWidth, blockLength)

        // Создаем все блоки
        list.addAll(
            addRotatedBlocks(
                standardBlock = block,
                blockLength = blockLength,
                blockWidth = blockWidth,
                xCount = lengthCountInverted,
                yCount = optimalChoice,
                density = density,
                isRotated = !isRotated
            )
        )

        val widthCount = (palletWidth - optimalChoice * blockLength) / blockWidth
        val widthShift = optimalChoice * blockLength
        list.addAll(
            printBlocks(
                block = block,
                blockLength = blockLength,
                blockWidth = blockWidth,
                density = density,
                xCount = lengthCount,
                yCount = widthCount,
                yShift = widthShift,
                isRotated = isRotated
            )
        )

        return list
    }

    private fun printBlocks(
        block: Block,
        blockLength: Int,
        blockWidth: Int,
        density: Density,
        xCount: Int,
        yCount: Int,
        xShift: Int = 0,
        yShift: Int = 0,
        isRotated: Boolean
    ): List<Block> {
        val list = mutableListOf<Block>()

        for (i in 0 until xCount) {
            for (j in 0 until yCount) {
                list.add(
                    block.copy(
                        printX = (xShift + blockLength * i).dp.toInt(density),
                        printY = (yShift + blockWidth * j).dp.toInt(density),
                        x = xShift + blockLength * i,
                        y = yShift + blockWidth * j,
                        isRotated = isRotated
                    )
                )
            }
        }

        return list
    }

    private fun addRotatedBlocks(
        standardBlock: Block,
        blockLength: Int,
        blockWidth: Int,
        xCount: Int,
        yCount: Int,
        density: Density,
        isRotated: Boolean
    ): List<Block> {
        val list = mutableListOf<Block>()

        for (i in 0 until xCount) {
            for (j in 0 until yCount) {
                list.add(
                    standardBlock.copy(
                        width = blockLength,
                        length = blockWidth,
                        printX = (blockWidth.dp.toInt(density)) * i,
                        printY = (blockLength.dp.toInt(density)) * j,
                        x = blockWidth * i,
                        y = blockLength * j,
                        isRotated = isRotated
                    )
                )
            }
        }

        return list
    }

    /**
     * Определение самого выгодного варианта размещения блоков
     *
     */
    private fun optimalBlocks(
        palletMainSize: Int,
        palletSecondSize: Int,
        blockMainSize: Int,
        blockSecondSize: Int
    ): Int {
        val mainSizeCount = palletSecondSize / blockSecondSize
        val mainSizeCountInverted = palletSecondSize / blockMainSize

        var distanceWithoutInvertedBlocks = palletMainSize

        var index = 0
        var optimalIndex = 0
        var maxBlock = 0

        do {
            var blocks: Int = (index * mainSizeCountInverted).toInt()
            val horizontalCount = (distanceWithoutInvertedBlocks / blockMainSize).toInt()
            blocks += (horizontalCount * mainSizeCount).toInt()

            if (maxBlock < blocks) {
                maxBlock = blocks
                optimalIndex = index
            }

            index++
            distanceWithoutInvertedBlocks = palletMainSize - index * blockSecondSize
        } while (distanceWithoutInvertedBlocks > 0)

        return optimalIndex
    }
}