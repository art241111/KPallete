package com.ger.common.layoutScreen.dragableBoxView.data

import com.ger.common.data.Product

data class Block(
    val product: Product,
    val overhang: Int = 0,
    var printX: Int = 0,
    var printY: Int = 0,
    val x: Int = 0,
    val y: Int = 0,
    val isRotated: Boolean = false
) {
    override fun toString(): String {
        return "$product,$overhang,$printX,$printY,$x,$y,$isRotated"
    }

    companion object {
        fun fromString(string: String): Block {
            val split = string.split(",")
            return Block(
                product = Product.fromString(split[0].trim()),
                overhang = split[1].trim().toInt(),
                printX = split[2].trim().toInt(),
                printY = split[3].trim().toInt(),
                x = split[4].trim().toInt(),
                y = split[5].trim().toInt(),
                isRotated = split[6].trim().toBooleanStrict(),
            )
        }
    }
}