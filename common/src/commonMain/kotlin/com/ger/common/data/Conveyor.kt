package com.ger.common.data

class Conveyor(
    override var name: String = "",
    val takePosition: Point = Point()
) : WithName {
    override fun toString(): String {
        return "$name;$takePosition"
    }

    companion object {
        fun fromString(string: String): Conveyor {
            val split = string.split(";")
            return Conveyor(
                name = split[0],
                takePosition = Point.fromString(split[1]),
            )
        }
    }
}
