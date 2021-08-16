package com.ger.common.layoutScreen.dragableBoxView.data

data class Block(
    val overhang: Int,
    val width: Int,
    val length: Int,
    val height: Int,
    val weight: Double,
    val printX: Int = 0,
    val printY: Int = 0,
    val x: Int = 0,
    val y: Int = 0,
    val isRotated: Boolean = false
) {
    override fun toString(): String {
        return "$overhang,$width,$length,$printX,$printY,$x,$y,$isRotated,$height,$weight"
    }

    companion object {
        fun fromString(string: String): Block {
            val split = string.split(",")
            return Block(
                overhang = split[0].trim().toInt(),
                width = split[1].trim().toInt(),
                length = split[2].trim().toInt(),
                printX = split[3].trim().toInt(),
                printY = split[4].trim().toInt(),
                x = split[5].trim().toInt(),
                y = split[6].trim().toInt(),
                isRotated = split[7].trim().toBooleanStrict(),

                height = split[8].trim().toInt(),
                weight = split[9].trim().toDouble(),
            )
        }
    }
}