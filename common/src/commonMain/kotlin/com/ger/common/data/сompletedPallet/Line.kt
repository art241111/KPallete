package com.ger.common.data.сompletedPallet

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.ger.common.data.Pallet
import com.ger.common.data.Product
import com.ger.common.layoutScreen.dragableBoxView.data.Block

class Line(
    val name: String = "",
    val pallet: Pallet = Pallet(),
    val product: Product = Product(),
    val overhang: Int = 0,
    val distancesBetweenProducts: Int = 0,
    val layouts: State<List<Block>> = mutableStateOf(listOf())
) {
    override fun toString(): String {
        return "$name;$pallet;$product;$overhang;$distancesBetweenProducts;${layouts.value.joinToString("/")}"
    }

    companion object {
        fun fromString(string: String): Line {
            val split = string.split(";")
            val name = split[0]
            val pallet = Pallet.fromString(split[1])
            val product = Product.fromString(split[2])
            val overhang = split[3]
            val distancesBetweenProducts = split[4]

            val layoutsSplit = split[5].split("/")

            val layouts = mutableListOf<Block>()
            for (layout in layoutsSplit) {
                layouts.add(Block.fromString(layout))
            }

            return Line(
                name = name,
                pallet = pallet,
                product = product,
                overhang = overhang.trim().toInt(),
                distancesBetweenProducts = distancesBetweenProducts.trim().toInt(),
                layouts = mutableStateOf(layouts)
            )
        }
    }
}