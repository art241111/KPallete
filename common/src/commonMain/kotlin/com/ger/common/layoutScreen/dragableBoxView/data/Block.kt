package com.ger.common.layoutScreen.dragableBoxView.data

import com.ger.common.data.Product

data class Block(
    val product: Product,
    val overhang: Int = 0,
    var x: Int = 0,
    var y: Int = 0,
    val isRotated: Boolean = false
) {
    override fun toString(): String {
        return "$product,$overhang,$x,$y,$isRotated"
    }

    companion object {
        fun fromString(string: String): Block {
            val split = string.split(",")
            return Block(
                product = Product.fromString(split[0].trim()),
                overhang = split[1].trim().toInt(),
                x = split[2].trim().toInt(),
                y = split[3].trim().toInt(),
                isRotated = split[4].trim().toBooleanStrict(),
            )
        }
    }
}