package com.ger.common.data

data class Product(
    override val name: String = "",
    val width: Int = 0,
    val length: Int = 0,
    val height: Int = 0,
    val weight: Double = -1.0,
) : WithName {
    override fun toString(): String {
        return "$name!$width!$length!$height!$weight"
    }

    companion object {
        fun fromString(string: String): Product {
            val split = string.split("!")
            return Product(
                name = split[0],
                width = split[1].trim().toInt(),
                length = split[2].trim().toInt(),
                height = split[3].trim().toInt(),
                weight = split[4].trim().toDouble()
            )
        }
    }
}
