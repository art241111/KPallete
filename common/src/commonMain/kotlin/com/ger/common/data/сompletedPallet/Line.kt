package com.ger.common.data.сompletedPallet

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.ger.common.layoutScreen.dragableBoxView.data.Block

class Line(
    val overhang: Int = 0,
    val distancesBetweenProducts: Int = 0,
    val layouts: State<List<Block>> = mutableStateOf(listOf())
) {
    override fun toString(): String {
        return "$overhang;$distancesBetweenProducts;${layouts.value.joinToString("/")}"
    }

    companion object {
        fun fromString(string: String): Line {
            val split = string.split(";")
            val overhang = split[1]
            val distancesBetweenProducts = split[2]

            val layoutsSplit = split[3].split("/")

            val layouts = mutableListOf<Block>()
            for (layout in layoutsSplit) {
                layouts.add(Block.fromString(layout))
            }

            return Line(
                overhang = overhang.trim().toInt(),
                distancesBetweenProducts = distancesBetweenProducts.trim().toInt(),
                layouts = mutableStateOf(layouts)
            )
        }
    }
}