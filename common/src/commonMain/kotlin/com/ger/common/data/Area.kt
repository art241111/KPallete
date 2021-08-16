package com.ger.common.data

data class Area(
    override var name: String = "",
    var leftTopPosition: Point = Point(),
    var rightTopPosition: Point = Point(),
    var leftBottomPosition: Point = Point(),
) : WithName {
    override fun toString(): String {
        return "$name;$leftTopPosition;$rightTopPosition;$leftBottomPosition"
    }

    companion object {
        fun fromString(string: String): Area {
            val split = string.split(";")
            return Area(
                name = split[0],
                leftTopPosition = Point.fromString(split[1]),
                rightTopPosition = Point.fromString(split[2]),
                leftBottomPosition = Point.fromString(split[3]),
            )
        }
    }
}
