package com.ger.common.data

data class Pallet(
    override val name: String = "",
    val width: Int = 0,
    val length: Int = 0,
    val height: Int = 0,
) : WithName {
    override fun toString(): String {
        return "$name.$width.$length.$height"
    }

    companion object {
        fun fromString(string: String): Pallet {
            val split = string.split(".")
            return Pallet(
                name = split[0],
                width = split[1].trim().toInt(),
                length = split[2].trim().toInt(),
                height = split[3].trim().toInt()
            )
        }
    }
}
